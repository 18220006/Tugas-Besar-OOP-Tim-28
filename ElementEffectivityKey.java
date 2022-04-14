public class ElementEffectivityKey {
    private ElementType source;
    private ElementType target;

    public ElementEffectivityKey(ElementType source, ElementType target){
        this.source = source;
        this.target = target;
    }

    public boolean equals(Object object) {
        if(object instanceof ElementEffectivityKey) {
            ElementEffectivityKey s = (ElementEffectivityKey)object;
            return source == s.source && target == s.target;
        }
        return false;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = result * prime + source.name().hashCode();
        result = result * prime + target.name().hashCode();
        return result;
    }
}