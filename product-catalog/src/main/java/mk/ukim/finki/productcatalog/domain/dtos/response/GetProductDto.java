package mk.ukim.finki.productcatalog.domain.dtos.response;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetProductDto implements Serializable {
    private Long id;

    private String name;

    private String description;

    private Float price;

    private Float starRating;

    private Integer numRatings;

    private String brand;

    private String code;

    private List<GetSizeDto> sizes;

    private List<String> images;

    private Boolean isInShoppingCart;

    private Boolean isInWishlist;
}
