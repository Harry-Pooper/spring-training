package main.java.util;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Ваня on 01.06.2018.
 */
@Component
public class SearchUtil {

    public String makeQueryForSearch(String title, String orgName, String city, String sfrom, String sto, String tagss){
        StringBuilder query = new StringBuilder("select distinct ov.vc_id from org_vacansys ov, org_profile op, tags t, tags_vc tv where ov.profileorg_id = op.profileorg_id and ov.vc_id = tv.vc_id and t.tag_id = tv.tag_id and ov.ispublic = true");

        if(!StringUtil.isNullOrEmpty(title)){
            query.append(" and lower(ov.name_vacansys) like lower('%").append(title).append("%')");
        }

        if(!StringUtil.isNullOrEmpty(orgName)){
            query.append(" and lower(op.org_name) like lower('%").append(orgName).append("%')");
        }

        if(!StringUtil.isNullOrEmpty(city)){
            query.append(" and op.city_id = ").append(city);
        }


        if(!StringUtil.isNullOrEmpty(sfrom)){
            query.append(" and ov.salary_from > ").append(sfrom);
        }
        if(!StringUtil.isNullOrEmpty(sto)){
            query.append(" and ov.salary_to <").append(sto);
        }
        if(!StringUtil.isNullOrEmpty(tagss)){
            String[] tagssarr = tagss.trim().split("\\s*,\\s*");
            query.append(" and lower(t.tag_info) like any ('{");
            int i=0;
            for(String tag : tagssarr){
                if (i>0) {
                    query.append(",");
                }
                query.append("\"%").append(tag).append("%\"");
                i=i+1;
            }
            query.substring(0, query.length() - 1);
            query.append("}')");
        }

        return query.toString();
    }

    public String makeQueryForSearch(String firstName, String secondName, String lastName, String title, String position, String paymentFrom, String paymentTo, String tags){
        StringBuilder query = new StringBuilder("select distinct c.cv_id from cv c, user_profile up, tags_cv tc, tags t, payment p, position pos, position_cv posc where up.profile_id = c.profile_id and pos.position_id = posc.position_id and posc.cv_id = c.cv_id and p.payment_id = c.payment_id and c.cv_id = tc.cv_id and tc.tag_id = t.tag_id and c.is_public = true");
     //   List<String> tagsForSearch = new ArrayList<>();
/*
        if(!StringUtil.isNullOrEmpty(tags)){
            tagsForSearch = Arrays.asList(tags.split(","));
        }
        */

        if(!StringUtil.isNullOrEmpty(firstName)){
            query.append(" and lower(up.first_name) like lower('%").append(firstName).append("%')");
        }

        if(!StringUtil.isNullOrEmpty(secondName)){
            query.append(" and lower(up.second_name) like lower('%").append(secondName).append("%')");
        }

        if(!StringUtil.isNullOrEmpty(lastName)){
            query.append(" and lower(up.last_name) like lower('%").append(lastName).append("%')");
        }

        if(!StringUtil.isNullOrEmpty(title)){
            query.append(" and lower(c.title) like lower('%").append(title).append("%')");
        }

        if(!StringUtil.isNullOrEmpty(position)){
            query.append(" and posc.position_id = ").append(position);
        }

        if(!StringUtil.isNullOrEmpty(paymentFrom)){
            query.append(" and p.payment_from > ").append(paymentFrom);
        }

        if(!StringUtil.isNullOrEmpty(paymentTo)){
            query.append(" and p.payment_to < ").append(paymentTo);
        }

        if(!StringUtil.isNullOrEmpty(tags)){
            String[] tagsarr = tags.trim().split("\\s*,\\s*");
            query.append(" and lower(t.tag_info) ilike any ('{");
            int i=0;
            for(String tag : tagsarr){
                if (i>0) {
                    query.append(",");
                }
                query.append("\"%").append(tag).append("%\"");
                i=i+1;
            }
            query.substring(0, query.length() - 1);
            query.append("}')");
        }

        /*
        if(!tagsForSearch.isEmpty()){
            query.append(" and lower(t.tag_info) like any ('{");
            for(String tag : tagsForSearch){
                query.append("lower(\"%").append(tag).append("%\"),");
            }
            query.substring(0, query.length() - 1);
            query.append("}')");
        }
        */
        return query.toString();
    }

}
