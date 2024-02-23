package backend.atm.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public class SuperEntity implements Cloneable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="pk", nullable = false)
    protected Long pk;
    @CreationTimestamp
    protected LocalDateTime createdTime;
    @UpdateTimestamp
    protected LocalDateTime updatedTime;
}