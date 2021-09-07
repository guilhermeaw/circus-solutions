package editors;

import entities.Occupation;

import java.util.List;

import common.DefaultEditor;
import common.EditorCallback;

public class OccupationEditor extends DefaultEditor<Occupation> {
    public OccupationEditor(EditorCallback<Occupation> callback) {
        super(callback);

        initComponents();
    }

    @Override
    protected void validateInput(List<String> errors) throws Exception {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void obtainInput() {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void setSource(Occupation source) {
        // TODO Auto-generated method stub
        
    }
}
