package mk.ukim.finki.productcatalog.util;

public class QueryConstants {
    public final static String isProductInWishlist = "select exists(select id from wishlist w\n" +
            "    inner join wishlist_products wp on w.id = wp.wishlist_id\n" +
            "    where w.user_id=:userId and product_id=:productId and w.is_deleted=false)";
}
