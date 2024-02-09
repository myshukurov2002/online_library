package com.company.cart.services;

import com.company.base.ApiResponse;
import com.company.book.dtos.BookResp;
import com.company.book.servies.BookService;
import com.company.cart.dtos.CartCr;
import com.company.cart.dtos.CartResp;
import com.company.cart.entities.CartEntity;
import com.company.cart.repositories.CartRepository;
import com.company.config.i18n.MessageService;
import com.company.config.security.utils.securityUtil;
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
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final BookService bookService;
    private final MessageService messageService;

    @Override
    public ApiResponse<CartResp> create(CartCr cartCr) {

        cartRepository
                .findByBookIdAndUserIdAndVisibilityTrue(cartCr.bookId(), securityUtil.getCurrentUserId())
                .ifPresent(c -> {
                    throw new DuplicateItemException();
                });

        CartEntity cart = toEntity(cartCr);

        cartRepository.save(cart);

        log.info("cart created id: " + cart.getId());

        return new ApiResponse<>(true, messageService.getMessage("success.created"), toDto(cart));
    }

    @Override
    public ApiResponse<String> delete(UUID id) {

        CartEntity cart = get(id);

        cart.setVisibility(false);
        cartRepository.save(cart);

        return new ApiResponse<>(true, messageService.getMessage("success.deleted"));
    }

    @Override
    public ApiResponse<CartResp> getById(UUID id) {

        return new ApiResponse<>(true, toDto(get(id)));
    }

    @Override
    public ApiResponse<List<CartResp>> getAllByOwner() {

        List<CartResp> carts = cartRepository
                .findAllByUserIdAndCartStatusFalseAndVisibilityTrue(securityUtil.getCurrentUserId())
                .stream()
                .map(this::toDto)
                .toList();

        return new ApiResponse<>(true, carts);
    }

    @Override
    public ApiResponse<List<CartResp>> getAllPurchasedBooks() {

        List<CartResp> carts = cartRepository
                .findAllByUserIdAndCartStatusTrueAndVisibilityTrue(securityUtil.getCurrentUserId())
                .stream()
                .map(this::toDto)
                .toList();

        return new ApiResponse<>(true, carts);
    }

    @Override
    public ApiResponse<CartResp> update(UUID id) {

        CartEntity cart = null;
        try {
            cart = get(id);

        } catch (ItemNotFoundException e) {

            BookResp book = bookService
                    .getById(id)
                    .data();

            cart = CartEntity
                    .builder()
                    .cartStatus(true)
                    .userId(securityUtil.getCurrentUserId())
                    .bookId(book.id())
                    .build();

            return new ApiResponse<>(true, toDto(cart));
        }

        cart.setCartStatus(true);
        cartRepository.save(cart);

        return new ApiResponse<>(true, toDto(cart));
    }

    private CartEntity get(UUID id) {

        return cartRepository
                .findByIdAndVisibilityTrue(id)
                .orElseThrow(ItemNotFoundException::new);
    }

    private CartResp toDto(CartEntity cart) {

        return CartResp.builder()
                .id(cart.getId())
                .book(
                        bookService
                                .getById(cart.getBookId())
                                .data()
                ).build();
    }

    private CartEntity toEntity(CartCr cartCr) {

        return CartEntity.builder()
                .userId(securityUtil.getCurrentUserId())
                .cartStatus(false)
                .bookId(cartCr.bookId()).build();
    }
}
