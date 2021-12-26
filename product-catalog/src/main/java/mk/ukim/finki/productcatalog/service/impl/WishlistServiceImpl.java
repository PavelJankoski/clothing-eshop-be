package mk.ukim.finki.productcatalog.service.impl;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.productcatalog.repository.WishlistRepository;
import mk.ukim.finki.productcatalog.service.WishlistService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {
    private final WishlistRepository wishlistRepository;
}
