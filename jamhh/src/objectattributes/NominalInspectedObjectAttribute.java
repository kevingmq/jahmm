package objectattributes;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;
import utils.Utils;

/**
 *
 * @author kommusoft
 */
public class NominalInspectedObjectAttribute<TSource,TTarget> extends NominalObjectAttribute<TSource,TTarget> {
    
    private final String name;
    private final Method method;
    private final Class<?> resultclass;

    NominalInspectedObjectAttribute(Method method, String name, Class<?> resultclass) {
        this.name = name;
        this.method = method;
        this.resultclass = resultclass;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Set<TTarget> getPossibleValues() {
        return (Set<TTarget>) Utils.getNominalSet(this.resultclass);
    }

    @Override
    @SuppressWarnings("unchecked")
    public TTarget getAttribute(TSource source) throws IllegalAccessException, InvocationTargetException {
        return (TTarget) this.method.invoke(source);
    }

    @Override
    public String getName() {
        return this.name;
    }
    
}