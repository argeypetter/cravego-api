package entity;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int _id;

    @Column(nullable = false, length = 80, unique = true)
    private String _name;

    @Column(length = 255)
    private String _description;

    @Column(nullable = false)
    private int _price;

    @Column(length = 255)
    private String _imagen;

    @Column(nullable = false)
    private int _stock;

    @Column(nullable = false)
    private boolean _available;

    @ManyToOne
    @JoinColumn(name = "_id", nullable = false)
    private Category _category;
}
