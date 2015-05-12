package sebcel.genealogia.gui.lista;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import sebcel.genealogia.struct.ReferenceListElement;

public class MultiValueReferenceModel implements ListModel {

    public static final long serialVersionUID = 0L;

    private List<ReferenceListElement> data = new ArrayList<ReferenceListElement>();
    private Set<ListDataListener> listeners = new HashSet<ListDataListener>();

    public MultiValueReferenceModel() {
        super();
    }

    public void setData(List<ReferenceListElement> data) {
        this.data = data;
    }

    public List<ReferenceListElement> getData() {
        return data;
    }

    public void addElement(ReferenceListElement element) {
        if (!data.contains(element)) {
            data.add(element);
            for (ListDataListener l : listeners) {
                l.contentsChanged(new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, 0, data.size() - 1));
            }
        }
    }

    public void removeElement(ReferenceListElement element) {
        if (data.contains(element)) {
            data.remove(element);
            for (ListDataListener l : listeners) {
                l.contentsChanged(new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, 0, data.size() - 1));
            }
        }
    }

    @Override
    public int getSize() {
        return data.size();
    }

    @Override
    public Object getElementAt(int index) {
        return data.get(index).getDescription();
    }

    public ReferenceListElement getElementObjectAt(int index) {
        return data.get(index);
    }

    @Override
    public void addListDataListener(ListDataListener l) {
        listeners.add(l);
    }

    @Override
    public void removeListDataListener(ListDataListener l) {
        listeners.remove(l);
    }
}