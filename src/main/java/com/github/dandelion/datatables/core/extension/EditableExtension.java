package com.github.dandelion.datatables.core.extension;

import com.github.dandelion.datatables.core.html.HtmlTable;

/**
*/
public class EditableExtension extends AbstractExtension {

    public static final String EXTENSION_NAME = "editable";

    @Override
    public void setup(HtmlTable table) {
        System.out.println("table = " + table);
//        appendToComponentConfiguration();
//        appendToBeforeStartDocumentReady("new $.fn.dataTable.Editor()");
//        appendToAfterStartDocumentReady(("$('#myTableId').dataTable().makeEditable({\n" +
//                "                    sDeleteHttpMethod: \"DELETE\",\n" +
//                "                    sDeleteURL: \"/ajax/persons\",\n" +
//                "                    sDeleteRowButtonId: \"btnDeleteRow\"\n" +
//                "                });"));

    }

    @Override
    public String getExtensionName() {
        return EXTENSION_NAME;
    }
}
