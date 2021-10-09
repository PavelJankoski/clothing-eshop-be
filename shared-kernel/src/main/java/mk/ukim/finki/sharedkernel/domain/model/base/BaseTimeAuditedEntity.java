package mk.ukim.finki.sharedkernel.domain.model.base;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.sql.Timestamp;
import java.time.Instant;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeAuditedEntity {

    @CreatedDate
    @Column(name = "created_on", insertable = false,
            columnDefinition="TIMESTAMP DEFAULT now()")
    private Timestamp createdOn;

    @LastModifiedDate
    @Column(name = "modified_on", insertable = false)
    private Timestamp modifiedOn;
}
