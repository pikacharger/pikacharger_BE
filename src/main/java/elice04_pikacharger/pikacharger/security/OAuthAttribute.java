//package elice04_pikacharger.pikacharger.security;
//
//import elice04_pikacharger.pikacharger.domain.user.entity.User;
//import lombok.Builder;
//import lombok.Getter;
//
//import java.util.Map;
//
//@Getter
//public class OAuthAttribute {
//    private Map<String, Object> attributes;
//    private String nameAttributeKey;
//    private String name;/*/
//    private String email;
//    private String picture;
//
//    @Builder
//    public OAuthAttribute(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String picture) {
//        this.attributes = attributes;
//        this.nameAttributeKey = nameAttributeKey;
//        this.name = name;
//        this.email = email;
//        this.picture = picture;
//    }
//
//    public static OAuthAttribute of(String registrationId, String userNameAttributeName, Map<String,Object> attributes){
//        return ofGoogle(userNameAttributeName,attributes);
//    }
//
//    public static OAuthAttribute ofGoogle(String userNameAttributeName, Map<String,Object> attributes){
//        return OAuthAttribute.builder()
//                .name((String) attributes.get("name"))
//                .email((String) attributes.get("email"))
//                .picture((String) attributes.get("picture"))
//                .attributes(attributes)
//                .nameAttributeKey(userNameAttributeName)
//                .build();
//    }
//
//    public User toEnity(){
//        return User.builder()
//                .username(name)
//                .email(email)
//                .profileImage(picture)
//                //.role(UserRole)
//                .build();
//    }
//}
