package SchedulingApplication.util;

import java.util.Locale;
import java.util.ResourceBundle;

/** Utility class that handles functions related to localization. */
public class LanguageUtility {

    /** Contains the resourceBundle referenced to the language properties and default system Locale. */
    private static final ResourceBundle rb = ResourceBundle.getBundle(
            "SchedulingApplication/ResourceBundles/Lang", Locale.getDefault());

    /** gets a reference to the resourceBundle object.
     * @return      the resourceBundle object*/
    public static ResourceBundle getRb() {
        return rb;
    }
}
