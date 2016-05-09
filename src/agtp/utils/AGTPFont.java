package agtp.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author reza
 */
public class AGTPFont {

    private static HashMap<String, String> mChars;

    public static HashMap<String, String> getCharacters() {
        if (mChars == null) {
            HashMap<String, String> aChars = new HashMap<>();
            for (Icons v : Icons.values()) {
                aChars.put(v.name(), v.icon);
            }
            mChars = aChars;
        }
        return mChars;
    }

    public Collection<String> getIcons() {
        Collection<String> icons = new LinkedList<>();

        for (Icons value : Icons.values()) {
            icons.add(value.name());
        }

        return icons;
    }

    public static enum Icons {

        plus("\ue80f"), 
        trash("\ue835"), 
        pencil("\ue826"), 
        lock_open_alt("\ue815"), 
        up_open("\ue829"), 
        down_open("\ue828"), 
        download_1("\ue834"), 
        left_dir("\ue840"), 
        right_dir("\ue841"), 
        to_start("\ue845"), 
        to_end("\ue844"), 
        search("\ue842"), 
        cancel_1("\ue843"), 
        forward("\ue81b"),
        doc_new("\ue848"),
        floppy("\ue847"),
        login("\ue849"),
        edit("\ue827"),
        level_down("\ue84e"),
        export("\ue84d"),
        erja("\ue84c"),
        check("\ue84f"),
        eye("\ue817"); 

        /* '' */

        String icon;

        Icons(String character) {
            this.icon = character;
        }

        public String getFormattedName() {
            return "{" + name() + "}";
        }

        public String getIcon() {
            return icon;
        }

        public String getName() {
            return name();
        }
    }

}
