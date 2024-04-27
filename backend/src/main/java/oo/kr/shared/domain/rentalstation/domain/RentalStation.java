package oo.kr.shared.domain.rentalstation.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import oo.kr.shared.domain.umbrella.domain.Umbrella;
import oo.kr.shared.global.type.BaseEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class RentalStation extends BaseEntity {

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String address;

  @Column(nullable = false)
  private Double latitude;

  @Column(nullable = false)
  private Double longitude;

  @OneToMany(mappedBy = "currentStation", fetch = FetchType.LAZY)
  private List<Umbrella> availableUmbrellas = new ArrayList<>();

  public RentalStation(String name, String address, Double latitude, Double longitude) {
    this.name = name;
    this.address = address;
    this.latitude = latitude;
    this.longitude = longitude;
  }

}