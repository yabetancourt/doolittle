package com.yabetancourt.application.views.helloworld;

import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.yabetancourt.application.utils.DecompositionException;
import com.yabetancourt.application.utils.IrregularMatrixException;
import com.yabetancourt.application.utils.NullDiagonalException;
import com.yabetancourt.application.utils.Solver;

@PageTitle("Doolittle")
@Route(value = "doolittle")
public class DoolittleView extends HorizontalLayout {

    public DoolittleView(){
        setSizeFull();
        setWidthFull();
        setJustifyContentMode(JustifyContentMode.BETWEEN);
        TextArea matrixInput = new TextArea("Matriz Ampliada");
        matrixInput.setWidthFull();
        matrixInput.setMinHeight(200, Unit.PIXELS);
        matrixInput.setClearButtonVisible(true);
        TextField systemOutput = new TextField("Solución del sistema");
        systemOutput.setWidthFull();
        TextField medVector = new TextField("Vector Intermedio");
        medVector.setWidthFull();
        VerticalLayout solLayout = new VerticalLayout(medVector, systemOutput);
        solLayout.setWidthFull();
        TextArea lowerMatrix = new TextArea("L");
        lowerMatrix.setWidthFull();
        TextArea upperMatrix = new TextArea("U");
        upperMatrix.setWidthFull();
        HorizontalLayout decompositionLayout = new HorizontalLayout(lowerMatrix, upperMatrix);
        decompositionLayout.setWidthFull();
        VerticalLayout answerLayout = new VerticalLayout(decompositionLayout, solLayout);

        Button start = new Button("Resolver");
        start.setWidthFull();
        start.addClickListener(click -> {
            try {
                Solver solver = new Solver(matrixInput.getValue());
                lowerMatrix.setValue(solver.getL());
                upperMatrix.setValue(solver.getU());
                medVector.setValue(solver.getY());
                systemOutput.setValue(solver.getX());
                Notification.show("Solucionado Correctamente").addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            } catch (IrregularMatrixException e) {
                Notification.show("Matriz Incorrecta").addThemeVariants(NotificationVariant.LUMO_ERROR);
            }catch (NumberFormatException e){
                Notification.show("Error de Formato").addThemeVariants(NotificationVariant.LUMO_ERROR);
            }catch (DecompositionException e){
                Notification.show("La Matriz no puede ser descompuesta").addThemeVariants(NotificationVariant.LUMO_ERROR);
            }catch (NullDiagonalException e){
                Notification.show("Diagonal con elementos nulos").addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
        });
        VerticalLayout inputLayout = new VerticalLayout(matrixInput, start);
        add(inputLayout, answerLayout);
    }

}
