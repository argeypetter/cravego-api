package entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "category")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long _id;

    @Column(nullable = false, length = 80, unique = true)
    private String _name;

    @Column(length = 255)
    private String _description;

    @Column(nullable = false)
    private boolean _asset = true;

}
