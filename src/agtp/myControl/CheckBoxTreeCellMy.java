/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agtp.myControl;

import agtp.myControl.CellUtils;
import agtp.AGTP;
import agtp.dataBase.tables.Permission;
import agtp.utils.AGTPFont;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.text.Font;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 *
 * @author reza
 */
public class CheckBoxTreeCellMy extends DefaultTreeCellMy<Permission> {

    private boolean disabled = false;
    private final CheckBox checkBox;

    private ObservableValue<Boolean> booleanProperty;

    private BooleanProperty indeterminateProperty;

    private Font font;

    /**
     * *************************************************************************
     *                                                                         *
     * Constructors * *
     * ************************************************************************
     */
    /**
     * Creates a default {@link CheckBoxTreeCell} that assumes the TreeView is
     * constructed with {@link CheckBoxTreeItem} instances, rather than the
     * default {@link TreeItem}. By using {@link CheckBoxTreeItem}, it will
     * internally manage the selected and indeterminate state of each item in
     * the tree.
     */
    public CheckBoxTreeCellMy(boolean editable) {
        this();
        this.disabled = editable;
    }

    public CheckBoxTreeCellMy() {
        // getSelectedProperty as anonymous inner class to deal with situation
        // where the user is using CheckBoxTreeItem instances in their tree
        this(item -> {

            if (item instanceof CheckBoxTreeItem<?>) {
                return ((CheckBoxTreeItem<?>) item).selectedProperty();
            }
            return null;
        });
    }

    /**
     * Creates a {@link CheckBoxTreeCell} for use in a TreeView control via a
     * cell factory. Unlike {@link CheckBoxTreeCell#CheckBoxTreeCell()}, this
     * method does not assume that all TreeItem instances in the TreeView are
     * {@link CheckBoxTreeItem}.
     *
     * <p>
     * To call this method, it is necessary to provide a {@link Callback} that,
     * given an object of type TreeItem<T>, will return an
     * {@code ObservableValue<Boolean>} that represents whether the given item
     * is selected or not. This {@code ObservableValue<Boolean>} will be bound
     * bidirectionally (meaning that the CheckBox in the cell will set/unset
     * this property based on user interactions, and the CheckBox will reflect
     * the state of the {@code ObservableValue<Boolean>}, if it changes
     * externally).
     *
     * <p>
     * If the items are not {@link CheckBoxTreeItem} instances, it becomes the
     * developers responsibility to handle updating the state of parent and
     * children TreeItems. This means that, given a TreeItem, this class will
     * simply toggles the {@code ObservableValue<Boolean>} that is provided, and
     * no more. Of course, this functionality can then be implemented externally
     * by adding observers to the {@code ObservableValue<Boolean>}, and toggling
     * the state of other properties as necessary.
     *
     * @param getSelectedProperty A {@link Callback} that will return an
     * {@code ObservableValue<Boolean>} that represents whether the given item
     * is selected or not.
     */
    public CheckBoxTreeCellMy(
            final Callback<TreeItem<Permission>, ObservableValue<Boolean>> getSelectedProperty) {
        this(getSelectedProperty, CellUtils.<Permission>defaultTreeItemStringConverter(), null);
    }

    /**
     * Creates a {@link CheckBoxTreeCell} for use in a TreeView control via a
     * cell factory. Unlike {@link CheckBoxTreeCell#CheckBoxTreeCell()}, this
     * method does not assume that all TreeItem instances in the TreeView are
     * {@link CheckBoxTreeItem}.
     *
     * <p>
     * To call this method, it is necessary to provide a {@link Callback} that,
     * given an object of type TreeItem<T>, will return an
     * {@code ObservableValue<Boolean>} that represents whether the given item
     * is selected or not. This {@code ObservableValue<Boolean>} will be bound
     * bidirectionally (meaning that the CheckBox in the cell will set/unset
     * this property based on user interactions, and the CheckBox will reflect
     * the state of the {@code ObservableValue<Boolean>}, if it changes
     * externally).
     *
     * <p>
     * If the items are not {@link CheckBoxTreeItem} instances, it becomes the
     * developers responsibility to handle updating the state of parent and
     * children TreeItems. This means that, given a TreeItem, this class will
     * simply toggles the {@code ObservableValue<Boolean>} that is provided, and
     * no more. Of course, this functionality can then be implemented externally
     * by adding observers to the {@code ObservableValue<Boolean>}, and toggling
     * the state of other properties as necessary.
     *
     * @param getSelectedProperty A {@link Callback} that will return an
     * {@code ObservableValue<Boolean>} that represents whether the given item
     * is selected or not.
     * @param converter A StringConverter that, give an object of type
     * TreeItem<T>, will return a String that can be used to represent the
     * object visually.
     */
    public CheckBoxTreeCellMy(
            final Callback<TreeItem<Permission>, ObservableValue<Boolean>> getSelectedProperty,
            final StringConverter<TreeItem<Permission>> converter) {
        this(getSelectedProperty, converter, null);
    }

    private CheckBoxTreeCellMy(
            final Callback<TreeItem<Permission>, ObservableValue<Boolean>> getSelectedProperty,
            final StringConverter<TreeItem<Permission>> converter,
            final Callback<TreeItem<Permission>, ObservableValue<Boolean>> getIndeterminateProperty) {

        this.getStyleClass().add("check-box-tree-cell");
        setSelectedStateCallback(getSelectedProperty);
        setConverter(converter);
        font = Font.loadFont(AGTP.class.getResource("resourse/agtp_font.ttf").toExternalForm(), 14);
        this.checkBox = new CheckBox();
        this.checkBox.setAllowIndeterminate(false);
        this.checkBox.setFont(font);

        // by default the graphic is null until the cell stops being empty
        setGraphic(null);
    }

    /**
     * *************************************************************************
     *                                                                         *
     * Properties * *
     * ************************************************************************
     */
    // --- converter
    private ObjectProperty<StringConverter<TreeItem<Permission>>> converter
            = new SimpleObjectProperty<StringConverter<TreeItem<Permission>>>(this, "converter");

    /**
     * The {@link StringConverter} property.
     */
    public final ObjectProperty<StringConverter<TreeItem<Permission>>> converterProperty() {
        return converter;
    }

    /**
     * Sets the {@link StringConverter} to be used in this cell.
     */
    public final void setConverter(StringConverter<TreeItem<Permission>> value) {
        converterProperty().set(value);
    }

    /**
     * Returns the {@link StringConverter} used in this cell.
     */
    public final StringConverter<TreeItem<Permission>> getConverter() {
        return converterProperty().get();
    }

    // --- selected state callback property
    private ObjectProperty<Callback<TreeItem<Permission>, ObservableValue<Boolean>>> selectedStateCallback
            = new SimpleObjectProperty<Callback<TreeItem<Permission>, ObservableValue<Boolean>>>(
                    this, "selectedStateCallback");

    /**
     * Property representing the {@link Callback} that is bound to by the
     * CheckBox shown on screen.
     */
    public final ObjectProperty<Callback<TreeItem<Permission>, ObservableValue<Boolean>>> selectedStateCallbackProperty() {
        return selectedStateCallback;
    }

    /**
     * Sets the {@link Callback} that is bound to by the CheckBox shown on
     * screen.
     */
    public final void setSelectedStateCallback(Callback<TreeItem<Permission>, ObservableValue<Boolean>> value) {
        selectedStateCallbackProperty().set(value);
    }

    /**
     * Returns the {@link Callback} that is bound to by the CheckBox shown on
     * screen.
     */
    public final Callback<TreeItem<Permission>, ObservableValue<Boolean>> getSelectedStateCallback() {
        return selectedStateCallbackProperty().get();
    }

    /**
     * *************************************************************************
     *                                                                         *
     * Public API * *
     * ************************************************************************
     */
    /**
     * {@inheritDoc}
     */
    @Override
    public void updateItem(Permission item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty) {
            setText(null);
            setGraphic(null);
        } else {

            StringConverter<TreeItem<Permission>> c = getConverter();

            TreeItem<Permission> treeItem = getTreeItem();
            // update the node content
            setText(c != null ? item.getTitle() : (treeItem == null ? "" : treeItem.getValue().getTitle()));
            this.checkBox.setDisable(disabled);
            checkBox.setText(AGTPFont.Icons.valueOf(item.getIcon()).getIcon());
            checkBox.setStyle("-fx-text-fill: " + item.getIconColor() + ";-fx-opacity: 1");
            setStyle(getStyle() + "-fx-text-fill: " + item.getIconColor() + ";-fx-font-size: 12;");

            setGraphic(checkBox);

            // uninstall bindings
            if (booleanProperty != null) {
                checkBox.selectedProperty().unbindBidirectional((BooleanProperty) booleanProperty);
            }
            if (indeterminateProperty != null) {
                checkBox.indeterminateProperty().unbindBidirectional(indeterminateProperty);
            }

            // install new bindings.
            // We special case things when the TreeItem is a CheckBoxTreeItem
            if (treeItem instanceof CheckBoxTreeItem) {
                CheckBoxTreeItem<Permission> cbti = (CheckBoxTreeItem<Permission>) treeItem;
                booleanProperty = cbti.selectedProperty();
                checkBox.selectedProperty().bindBidirectional((BooleanProperty) booleanProperty);

                indeterminateProperty = cbti.indeterminateProperty();
                checkBox.indeterminateProperty().bindBidirectional(indeterminateProperty);
            } else {
                Callback<TreeItem<Permission>, ObservableValue<Boolean>> callback = getSelectedStateCallback();
                if (callback == null) {
                    throw new NullPointerException(
                            "The CheckBoxTreeCell selectedStateCallbackProperty can not be null");
                }

                booleanProperty = callback.call(treeItem);
                if (booleanProperty != null) {
                    checkBox.selectedProperty().bindBidirectional((BooleanProperty) booleanProperty);
                }
            }
        }

    }

    @Override
    void updateDisplay(Permission item, boolean empty) {
        // no-op
        // This was done to resolve RT-33603, but will impact the ability for
        // TreeItem.graphic to change dynamically.
    }
}
