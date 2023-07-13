package board.springjpacrud.entity;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public class BaseEntity {
    // 생성되었을 때 시간을 만들어주는 것.
    @CreationTimestamp
    // 수정 시에 관여하지 않겠다.
    @Column(updatable = false)
    private LocalDateTime createdTime;

    // 업데이트가 되었을 때 시간을 주겠다는 의미
    @UpdateTimestamp
    // 입력(insert) 시에 관여하지 않겠다.
    @Column(insertable = false)
    private LocalDateTime updatedTime;
}
