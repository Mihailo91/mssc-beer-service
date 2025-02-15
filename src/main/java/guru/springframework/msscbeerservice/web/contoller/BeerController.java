package guru.springframework.msscbeerservice.web.contoller;

import guru.springframework.msscbeerservice.services.BeerService;
import guru.springframework.msscbeerservice.web.model.BeerDto;
import guru.springframework.msscbeerservice.web.model.BeerPagedList;
import guru.springframework.msscbeerservice.web.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/")
@RestController
public class BeerController {

    private static final Integer PAGE_NUMBER = 0;
    private static final Integer PAGE_SIZE = 25;
    private final BeerService beerService;

    @GetMapping(path = "beer", produces = {"application/json"})
    public ResponseEntity<BeerPagedList> listBeers(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                   @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                   @RequestParam(value = "beerName", required = false) String beerName,
                                                   @RequestParam(value = "beerStyle", required = false) BeerStyleEnum beerStyle,
                                                   @RequestParam(value = "showInventoryOnHand", required = false) Boolean showInventoryOnHand){
        log.info("list beers");
        if(showInventoryOnHand == null){
            showInventoryOnHand = false;
        }

        if(pageNumber == null || pageNumber < 0){
            pageNumber = PAGE_NUMBER;
        }
        if(pageSize == null || pageSize < 0){
            pageSize = PAGE_SIZE;
        }

        return new ResponseEntity<>(beerService.listBeers(beerName, beerStyle, PageRequest.of(pageNumber, pageSize), showInventoryOnHand), HttpStatus.OK) ;
    }

    @GetMapping("/beer/{beerId}")
    public ResponseEntity<BeerDto> getBeerById(@PathVariable("beerId") UUID beerId,
                                               @RequestParam(value = "showInventoryOnHand", required = false) Boolean showInventoryOnHand) {
        log.info("getBeerById");
        if(showInventoryOnHand == null){
            showInventoryOnHand = false;
        }
        return new ResponseEntity<>(beerService.getById(beerId, showInventoryOnHand), HttpStatus.OK);
    }
    @GetMapping("beerUpc/{upc}")
    public ResponseEntity<BeerDto> getBeerByUpc(@PathVariable("upc") String  upc) {
        log.info("getBeerByUpc");

        return new ResponseEntity<>(beerService.getByUpc(upc), HttpStatus.OK);
    }

    @PostMapping(path = "beer/")
    public ResponseEntity<BeerDto> saveNewBeer(@Valid @RequestBody BeerDto beerDto) {
        log.info("saveNewBeer");
        return new ResponseEntity<>(beerService.saveNewBeer(beerDto), HttpStatus.CREATED);
    }

    @PutMapping("beer/{beerId}")
    public ResponseEntity<BeerDto> updateBeerById(@PathVariable("beerId") UUID beerId, @Valid @RequestBody BeerDto beerDto) {
        log.info("updateBeerById");
        return new ResponseEntity<>(beerService.updateBeerById(beerId, beerDto), HttpStatus.NO_CONTENT);
    }

}
