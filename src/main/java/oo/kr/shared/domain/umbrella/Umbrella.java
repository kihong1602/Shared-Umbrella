package oo.kr.shared.domain.umbrella;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import oo.kr.shared.domain.rentalstation.RentalStation;
import oo.kr.shared.global.utils.BaseEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Umbrella extends BaseEntity {

  @Enumerated(EnumType.STRING)
  private UmbrellaStatus umbrellaStatus;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "current_station")
  private RentalStation currentStation;

  public void rent() {
    currentStation = null;
  }

  public void returnToStation(RentalStation rentalStation) {
    umbrellaStatus = UmbrellaStatus.AVAILABLE;
    currentStation = rentalStation;
  }

  public void changeStatus(UmbrellaStatus status) {
    umbrellaStatus = status;
  }
}