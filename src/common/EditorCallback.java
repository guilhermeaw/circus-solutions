package common;

public abstract class EditorCallback<T> implements EventListener
{
    private T source;

    public EditorCallback( T source )
    {
        this.source = source;
    }

    public T getSource()
    {
        return source;
    }
}
