package oo.kr.shared.domain.rentalrecord.domain.repository.custom;

import static oo.kr.shared.domain.payment.domain.QPayment.payment;
import static oo.kr.shared.domain.rentalrecord.domain.QRentalRecord.rentalRecord;
import static oo.kr.shared.domain.user.domain.QUser.user;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import oo.kr.shared.domain.rentalrecord.domain.RentalRecord;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class QRentalRecordRepositoryImpl implements QRentalRecordRepository {

  private final JPAQueryFactory query;

  @Override
  public RentalRecord findRecentRentalRecordByEmail(String email) {
    return query.selectFrom(rentalRecord)
                .join(rentalRecord.payment, payment)
                .join(payment.user, user)
                .where(user.email.eq(email))
                .orderBy(rentalRecord.createDate.desc())
                .fetchFirst();
  }

  @Override
  public RentalRecord findRecentRentalRecordByEmailWithUmbrella(String email) {
    return query.selectFrom(rentalRecord)
                .join(rentalRecord.payment, payment)
                .join(payment.user, user)
                .fetchJoin()
                .where(user.email.eq(email))
                .orderBy(rentalRecord.createDate.desc())
                .fetchFirst();
  }
}
