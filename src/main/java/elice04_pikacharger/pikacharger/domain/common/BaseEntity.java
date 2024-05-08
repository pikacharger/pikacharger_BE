package elice04_pikacharger.pikacharger.domain.common;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass // BaseEntity를 상속한 엔티티들이 아래의 필드들을 자동으로 컬럼으로 인식하기 함.
@EntityListeners(AuditingEntityListener.class) //Auditing(자동으로 값 매핑)기능 추가
public abstract class BaseEntity {
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }
    /*
    @CreatedBy
    @Column(updatable = false)
    private String createBy;
     */
}
