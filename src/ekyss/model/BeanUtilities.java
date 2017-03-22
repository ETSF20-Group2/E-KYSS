package ekyss.model;

import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 *  Denna klass matar bönan utifrån innehållet i en given anrop.
 */
public class BeanUtilities {

    /** Examines all of the request parameters to see if
     *  any match a bean property (i.e., a setXxx method)
     *  in the object. If so, the request parameter value
     *  is passed to that method. If the method expects
     *  an int, Integer, double, Double, or any of the other
     *  primitive or wrapper types, parsing and conversion
     *  is done automatically. If the request parameter value
     *  is malformed (cannot be converted into the expected
     *  type), numeric properties are assigned zero and boolean
     *  properties are assigned false: no exception is thrown.
     *  @param fromBean The bean which will be populated.
     *  @param request The HttpServletRequest from which the bean will be populated.
     */
    public static void populateBean(Object fromBean, HttpServletRequest request) {
        populateBean(fromBean, request.getParameterMap());
    }

    /** Populates a bean based on a Map: Map keys are the
     *  bean property names; Map values are the bean property
     *  values. Type conversion is performed automatically as
     *  described above.
     *  @param bean The bean which will be populated.
     *  @param propertyMap A Map from which the bean will be populated.
     */
    public static void populateBean(Object bean, Map propertyMap) {
        try {
            BeanUtils.populate(bean, propertyMap);
        } catch(Exception e) {
            // Empty catch. The two possible exceptions are
            // java.lang.IllegalAccessException and
            // java.lang.reflect.InvocationTargetException.
            // In both cases, just skip the bean operation.
        }
    }
}
