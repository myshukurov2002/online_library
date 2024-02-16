package com.company.basket.services;

import com.company.base.ApiResponse;
import com.company.book.dtos.BookResp;
import com.company.book.servies.BookService;
import com.company.basket.dtos.BasketCr;
import com.company.basket.dtos.BasketResp;
import com.company.basket.entities.BasketEntity;
import com.company.basket.repositories.BasketRepository;
import com.company.config.i18n.MessageService;
import com.company.config.security.utils.securityUtil;
import com.company.expections.exp.AppBadRequestException;
import com.company.expections.exp.DuplicateItemException;
import com.company.expections.exp.ItemNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BasketServiceImpl implements BasketService {

    private final BasketRepository basketRepository;
    private final BookService bookService;
    private final MessageService messageService;

    @Override
    public ApiResponse<BasketResp> create(BasketCr basketCr) {

        basketRepository
                .findByBookIdAndUserIdAndVisibilityTrue(basketCr.bookId(), securityUtil.getCurrentUserId())
                .ifPresent(c -> {
                    throw new DuplicateItemException();
                });

        BasketEntity cart = toEntity(basketCr);

        basketRepository.save(cart);

        log.info("cart created id: " + cart.getId());

        return new ApiResponse<>(true, messageService.getMessage("success.created"), toDto(cart));
    }

    @Override
    public ApiResponse<String> delete(UUID id) {

        BasketEntity cart = get(id);

        cart.setVisibility(false);
        basketRepository.save(cart);

        return new ApiResponse<>(true, messageService.getMessage("success.deleted"));
    }

    @Override
    public ApiResponse<BasketResp> getById(UUID id) {

        return new ApiResponse<>(true, toDto(get(id)));
    }

    @Override
    public ApiResponse<List<BasketResp>> getAllByOwner() {

        List<BasketResp> carts = basketRepository
                .findAllByUserIdAndCartStatusFalseAndVisibilityTrue(securityUtil.getCurrentUserId())
                .stream()
                .map(this::toDto)
                .toList();

        return new ApiResponse<>(true, carts);
    }

    @Override
    public ApiResponse<List<BasketResp>> getAllPurchasedBooks() {

        List<BasketResp> carts = basketRepository
                .findAllByUserIdAndCartStatusTrueAndVisibilityTrue(securityUtil.getCurrentUserId())
                .stream()
                .map(this::toDto)
                .toList();

        return new ApiResponse<>(true, carts);
    }

    @Override
    public ApiResponse<BasketResp> update(UUID id) {

        BasketEntity cart = null;
        try {
            cart = get(id);

        } catch (ItemNotFoundException e) {

            basketRepository
                    .findByBookIdAndUserIdAndVisibilityTrue(id, securityUtil.getCurrentUserId())
                    .ifPresent(b -> {
                        if (b.getCartStatus()) {
                            throw new DuplicateItemException();
                        }
                    });

            BookResp book = bookService
                    .getById(id)
                    .data();

            cart = BasketEntity
                    .builder()
                    .cartStatus(true)
                    .userId(securityUtil.getCurrentUserId())
                    .bookId(book.id())
                    .build();

            basketRepository.save(cart);

            return new ApiResponse<>(true, toDto(cart));
        }

        if (cart.getCartStatus()) {

            return new ApiResponse<>(false, "book.already.purchased");
        }
        cart.setCartStatus(true);
        basketRepository.save(cart);

        return new ApiResponse<>(true, toDto(cart));
    }

    private BasketEntity get(UUID id) {

        return basketRepository
                .findByIdAndVisibilityTrue(id)
                .orElseThrow(ItemNotFoundException::new);
    }

    private BasketResp toDto(BasketEntity cart) {

        return BasketResp.builder()
                .id(cart.getId())
                .book(
                        bookService
                                .getById(cart.getBookId())
                                .data()
                ).build();
    }

    private BasketEntity toEntity(BasketCr basketCr) {

        return BasketEntity.builder()
                .userId(securityUtil.getCurrentUserId())
                .cartStatus(false)
                .bookId(basketCr.bookId()).build();
    }
}
