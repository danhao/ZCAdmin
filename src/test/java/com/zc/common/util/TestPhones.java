package com.zc.common.util;

import org.junit.Test;

import com.zc.common.web.util.WebUtils;

public class TestPhones {

    @Test
    public void test() {
        String ps = "95508 2011/12/8 20:13\n";
        ps += "qwe  ";
        ps += "1065752028881006\t2011/12/8 17:27\n";
        ps += "swd";
        ps += "95555\t2011/12/8 15:07\n";
        ps += "gws";
        // System.out.println(ps);

       /* String regex = "([\\d]+)(\\t|\\s)+|([\\d]{4}/[\\d]{1,2}/[\\d]{1,2} [\\d]{1,2}:[\\d]{1,2})|([\\w]{3})";
        Matcher m = Pattern.compile(regex).matcher(ps);
        List<Map<String, String>> smses = new ArrayList<Map<String, String>>();
        int i = 1;
        Map<String, String> oneSms = new LinkedHashMap<String, String>();
        while (m.find()) {
            String key = (i == 1) ? "phone" : (i == 2) ? "date" : "uid";
            oneSms.put(key, m.group().trim());
            if (i % 3 == 0) {
                i = 1;
                smses.add(oneSms);
                oneSms = new LinkedHashMap<String, String>();
                continue;
            }
            i++;
        }
*/
        System.out.println(WebUtils.getSearchSmses(ps));
    }

}
