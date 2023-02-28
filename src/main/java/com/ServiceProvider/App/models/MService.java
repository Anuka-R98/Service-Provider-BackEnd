package com.ServiceProvider.App.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "services")
public class MService {
    @Id
    private String id;

    @NotBlank
    @Size(max = 50)
    private String name;

    @NotBlank
    @Size(max = 500)
    @Email
    private String description;

    private List<Rating> ratings = new ArrayList<>();
    private double averageRating;

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
        this.averageRating = calculateAverageRating();
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    private double calculateAverageRating() {
        if (ratings == null || ratings.isEmpty()) {
            return 0;
        }

        int sum = 0;
        for (Rating rating : ratings) {
            sum += rating.getRating();
        }
        return (double) sum / ratings.size();
    }
}
