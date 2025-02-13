package guru.springframework.msscbeerservice.services;

import guru.springframework.msscbeerservice.web.model.BeerDto;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
import java.util.UUID;

public interface BeerService {

    BeerDto getById(UUID beerId);

    BeerDto saveNewBeer(@Valid BeerDto beerDto);

    BeerDto updateBeerById(UUID beerId, @Valid BeerDto beerDto);
}
