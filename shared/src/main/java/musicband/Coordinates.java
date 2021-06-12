package musicband;

import java.io.Serializable;
import java.util.Objects;

/**
 * Координаты
 */
public class Coordinates implements Serializable {
    private Long x; //Максимальное значение поля: 3, Поле не может быть null
    private Double y; //Значение поля должно быть больше -859, Поле не может быть null

    public Coordinates(Long x, Double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return Координата X
     */
    public Long getX() {
        return x;
    }

    /**
     * @return Координата Y
     */
    public Double getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return Objects.equals(getX(), that.getX()) && Objects.equals(getY(), that.getY());
    }

}
