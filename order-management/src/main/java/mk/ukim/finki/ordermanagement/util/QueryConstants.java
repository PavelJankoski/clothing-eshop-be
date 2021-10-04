package mk.ukim.finki.ordermanagement.util;

public class QueryConstants {
    public static final String isProductInShoppingCart = "SELECT EXISTS(\n" +
            "    select co.id from clothes_order co\n" +
            "        inner join order_item oi on co.id = oi.order_id\n" +
            "        where oi.product_id=:productId and co.user_id=:userId and co.status='PENDING')";
}
