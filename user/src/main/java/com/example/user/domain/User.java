package com.example.user.domain;

import com.example.user.constant.Constants;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Simple JavaBean domain object representing a user.
 *
 * @author Siddharth Mehta
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = Constants.USER_TABLE_NAME)
@EntityListeners(AuditingEntityListener.class)
public class User {
    public static final String USER_TABLE_NAME = "USER_TABLE";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(updatable = false)
    @CreationTimestamp
    private Timestamp createDate;

    @UpdateTimestamp
    private Timestamp lastModifiedDate;

    @Column(unique = true)
    private String userName;

    private String firstName;
    private String lastName;
}
