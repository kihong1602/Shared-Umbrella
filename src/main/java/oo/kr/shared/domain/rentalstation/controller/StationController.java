package oo.kr.shared.domain.rentalstation.controller;

import lombok.RequiredArgsConstructor;
import oo.kr.shared.domain.rentalstation.controller.request.SaveStationInfo;
import oo.kr.shared.domain.rentalstation.controller.response.NearRentalStation;
import oo.kr.shared.domain.rentalstation.service.StationService;
import oo.kr.shared.global.utils.Location;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class StationController {

  private final StationService stationService;

  @GetMapping("/station/near")
  public ResponseEntity<NearRentalStation> nearStation(@ModelAttribute Location location) {
    NearRentalStation nearStation = stationService.findNearStation(location);
    return ResponseEntity.ok(nearStation);
  }

  @PostMapping("/station/save")
  public void saveStation(@RequestBody SaveStationInfo stationInfo) {
    stationService.saveStation(stationInfo);
  }
}