package Creator;

import javax.swing.ComboBoxModel;
import javax.swing.ListModel;
import javax.swing.event.EventListenerList;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class ListAdapterComboBoxModel implements ComboBoxModel {

    private ListModel dataModel;
    private Object selectedObject;
    private DataModelListDataListenerAdapter listDataListenerAdapter;

    public ListAdapterComboBoxModel(ListModel ListModel) {
        dataModel = ListModel;
        this.listDataListenerAdapter = new DataModelListDataListenerAdapter();
        dataModel.addListDataListener(listDataListenerAdapter);
    }

    public int getSize() {
        return dataModel.getSize();
    }

    public Object getElementAt(int index) {
        return dataModel.getElementAt(index);
    }

    public void addListDataListener(ListDataListener l) {
        listDataListenerAdapter.addListDataListener(l);
    }

    public void removeListDataListener(ListDataListener l) {
        listDataListenerAdapter.removeListDataListener(l);
    }

    public void setSelectedItem(Object anObject) {
        if ((selectedObject != null && !selectedObject.equals(anObject))
                || selectedObject == null && anObject != null) {
            selectedObject = anObject;
            ListDataEvent e = new ListDataEvent(this,
                    ListDataEvent.CONTENTS_CHANGED, -1, -1);
            listDataListenerAdapter.delegateListDataEvent(e);
        }
    }

    public Object getSelectedItem() {
        return selectedObject;
    }

    private class DataModelListDataListenerAdapter implements ListDataListener {

        protected EventListenerList listenerList = new EventListenerList();

        public void removeListDataListener(ListDataListener l) {
            listenerList.remove(ListDataListener.class, l);
        }

        public void addListDataListener(ListDataListener l) {
            listenerList.add(ListDataListener.class, l);

        }

        public void intervalAdded(ListDataEvent e) {
            delegateListDataEvent(e);
        }

        public void intervalRemoved(ListDataEvent e) {
            checkSelection(e);
            delegateListDataEvent(e);
        }

        public void contentsChanged(ListDataEvent e) {
            checkSelection(e);
            delegateListDataEvent(e);
        }

        private void checkSelection(ListDataEvent e) {
            Object selectedItem = getSelectedItem();
            ListModel listModel = (ListModel) e.getSource();
            int size = listModel.getSize();
            boolean selectedItemNoLongerExists = true;
            for (int i = 0; i < size; i++) {
                Object elementAt = listModel.getElementAt(i);
                if (elementAt != null && elementAt.equals(selectedItem)) {
                    selectedItemNoLongerExists = false;
                    break;
                }
            }
            if (selectedItemNoLongerExists) {
                ListAdapterComboBoxModel.this.selectedObject = null;
            }
        }

        protected void delegateListDataEvent(ListDataEvent lde) {
            ListDataListener[] listeners = listenerList
                    .getListeners(ListDataListener.class);
            for (ListDataListener listDataListener : listeners) {
                listDataListener.contentsChanged(lde);
            }
        }

    }

}