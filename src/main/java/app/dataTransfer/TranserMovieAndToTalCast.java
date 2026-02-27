package app.dataTransfer;
import app.entities.Movie;
import lombok.Data;
import app.dto.ProductionDTO;
import org.hibernate.boot.spi.PropertyData;

@Data
public class TranserMovieAndToTalCast {
    private Movie movie;
    private PropertyData production;
}


