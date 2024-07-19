package com.andersen.ticketToRide.model;

import com.andersen.ticketToRide.enums.Cities;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.math.BigDecimal;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private Cities departure;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private Cities arrival;

    private int segments;

    private BigDecimal price;

    private String currency;

    private int traveller_amount;

    @ManyToOne
    @JoinColumn(name = "traveller_id", nullable = false)
    private Traveller traveller;
}