package mk.ukim.finki.productcatalog.domain.dtos.response;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetSizeDto implements Serializable{
    private Long id;

    private String size;
}
