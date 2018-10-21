package sender.filesController.propertiesChekers;

/*
klasa sprawdza poprawność ścieżki do przeszukiwanego folderu, poprawia braki w backslashach "\"
 */
public class PathSuffixChecker implements PathVeryfier {

    @Override
    public String isAdequate(String PathToVerify) {
        String VerifiedString;
        int lengthOfPath = PathToVerify.length();
        char backSlash = '\\';
        if ((PathToVerify.charAt(lengthOfPath - 1) == backSlash) && (PathToVerify.charAt(lengthOfPath - 2) == backSlash)) {
            VerifiedString = PathToVerify;
            return VerifiedString;
        }
        StringBuilder stringBuilder = new StringBuilder(PathToVerify);
        if ((PathToVerify.charAt(lengthOfPath - 1) == backSlash)) {
            VerifiedString = stringBuilder.append(backSlash).toString();
        } else
            VerifiedString = stringBuilder.append(backSlash).append(backSlash).toString();
        return VerifiedString;
    }
}
