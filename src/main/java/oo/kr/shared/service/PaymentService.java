package oo.kr.shared.service;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import oo.kr.shared.domain.member.Member;
import oo.kr.shared.domain.member.MemberRepository;
import oo.kr.shared.domain.payment.Payment;
import oo.kr.shared.domain.payment.PaymentRepository;
import oo.kr.shared.domain.rentalrecord.RentalRecord;
import oo.kr.shared.domain.rentalrecord.RentalRecordRepository;
import oo.kr.shared.domain.rentalstation.RentalStation;
import oo.kr.shared.domain.rentalstation.RentalStationRepository;
import oo.kr.shared.domain.umbrella.Umbrella;
import oo.kr.shared.domain.umbrella.UmbrellaRepository;
import oo.kr.shared.domain.umbrella.UmbrellaStatus;
import oo.kr.shared.dto.request.RequiredPaymentInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PaymentService {

  private final MemberRepository memberRepository;
  private final PaymentRepository paymentRepository;
  private final UmbrellaRepository umbrellaRepository;
  private final RentalStationRepository rentalStationRepository;
  private final RentalRecordRepository rentalRecordRepository;

  @Transactional
  public void completedPayment(RequiredPaymentInfo paymentInfo, Long memberId) {
    Member member = memberRepository.findById(memberId)
                                    .orElseThrow(RuntimeException::new);
    Payment payment = savePayment(paymentInfo, member);
    Payment savePayment = paymentRepository.save(payment);
    RentalStation rentalStation = changeAvailableUmbrellasInStation(paymentInfo.stationId());
    Umbrella umbrella = changeUmbrellaStatus(paymentInfo.umbrellaId());
    saveRentalRecord(savePayment.getCreateDate(), payment, umbrella, rentalStation);
  }

  private Payment savePayment(RequiredPaymentInfo paymentInfo, Member member) {
    Payment payment = new Payment(paymentInfo.impUid(), paymentInfo.merchantUid(), paymentInfo.amount(),
        member);
    return paymentRepository.save(payment);
  }

  private RentalStation changeAvailableUmbrellasInStation(Long rentalStationId) {
    RentalStation rentalStation = rentalStationRepository.findByIdWithPessimisticLock(rentalStationId)
                                                         .orElseThrow(RuntimeException::new);
    rentalStation.decreaseUmbrella();
    return rentalStationRepository.save(rentalStation);
  }

  private Umbrella changeUmbrellaStatus(Long umbrellaId) {
    Umbrella umbrella = umbrellaRepository.findById(umbrellaId)
                                          .orElseThrow(RuntimeException::new);
    umbrella.changeStatus(UmbrellaStatus.RENTED);
    return umbrella;
  }

  private void saveRentalRecord(LocalDateTime rentalTime, Payment payment, Umbrella umbrella,
      RentalStation rentalStation) {
    RentalRecord rentalRecord = new RentalRecord(rentalTime, payment, umbrella, rentalStation);
    rentalRecordRepository.save(rentalRecord);
  }
}