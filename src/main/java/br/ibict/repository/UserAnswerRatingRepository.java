package br.ibict.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ibict.domain.IDUserAnswerRating;
import br.ibict.domain.UserAnswerRating;

@Repository
public interface UserAnswerRatingRepository extends JpaRepository<UserAnswerRating, IDUserAnswerRating> {

}