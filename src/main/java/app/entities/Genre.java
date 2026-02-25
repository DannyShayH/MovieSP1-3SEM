package app.entities;

import app.interfaces.IEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Genre  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


}
