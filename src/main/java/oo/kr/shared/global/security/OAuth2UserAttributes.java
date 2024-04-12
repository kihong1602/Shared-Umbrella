package oo.kr.shared.global.security;

import java.util.Map;
import oo.kr.shared.domain.member.Member;
import oo.kr.shared.global.security.OAuth2ProviderRegistry.OAuth2ProviderType;

public record OAuth2UserAttributes(
    Map<String, Object> attributes,
    String nameAttributeKey,
    String providerType,
    String name,
    String email,
    String image
) {

  @SuppressWarnings("unchecked")
  public static OAuth2UserAttributes of(OAuth2ProviderType providerType, String userNameAttributeName,
      Map<String, Object> oAuth2UserResponse) {
    Map<String, Object> attributes = oAuth2UserResponse;

    if (providerType.hasAttributeField()) {
      attributes = (Map<String, Object>) oAuth2UserResponse.get(providerType.ATTRIBUTES_FIELD);
      Map<String, Object> kakaoAttributes = (Map<String, Object>) attributes.get("profile");
      return new OAuth2UserAttributes(
          oAuth2UserResponse,
          userNameAttributeName,
          providerType.name(),
          (String) kakaoAttributes.get(providerType.NAME_FIELD),
          (String) attributes.get(providerType.EMAIL_FIELD),
          (String) kakaoAttributes.get(providerType.IMAGE_FIELD));
    }

    return new OAuth2UserAttributes(
        oAuth2UserResponse,
        userNameAttributeName,
        providerType.name(),
        (String) attributes.get(providerType.NAME_FIELD),
        (String) attributes.get(providerType.EMAIL_FIELD),
        (String) attributes.get(providerType.IMAGE_FIELD)
    );
  }

  public Member toMember() {
    return new Member(name, email, image, providerType);
  }
}
