package mk.ukim.finki.ordermanagement.jobs;

import lombok.RequiredArgsConstructor;
import mk.ukim.finki.ordermanagement.domain.dtos.request.RemoveProductFromOrderDto;
import mk.ukim.finki.ordermanagement.domain.models.Order;
import mk.ukim.finki.ordermanagement.service.OrderItemService;
import mk.ukim.finki.ordermanagement.service.OrderService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Component
@EnableAsync
@RequiredArgsConstructor
public class RemoveOrderItemsIfExpiredJob {
    private final OrderService orderService;
    private final OrderItemService orderItemService;

    @Scheduled(cron = "0 0 */3 * * * ", zone = "CET")
    @Async
    public void checkExpiredOrderItems() {
        List<Order> pendingOrders = this.orderService.findAllPendingOrders();
        pendingOrders.forEach(po -> {
            po.getOrderItems().stream().filter(oi -> !oi.getIsDeleted()).forEach(oi -> {
                // order item older than one day
                if(Timestamp.from(Instant.now()).getTime() - oi.getCreatedOn().getTime() > 86400000) {
                    orderItemService.removeProductFromOrder(new RemoveProductFromOrderDto(po.getUserId(), oi.getProductId(), oi.getSizeId()));
                }
            });
        });
    }
}
