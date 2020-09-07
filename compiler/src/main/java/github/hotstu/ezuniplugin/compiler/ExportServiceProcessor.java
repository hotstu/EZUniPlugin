package github.hotstu.ezuniplugin.compiler;

import com.google.auto.service.AutoService;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

import github.hotstu.ezuniplugin.annotation.ModuleEntry;


@AutoService(Processor.class)
public class ExportServiceProcessor extends AbstractProcessor {
    private Elements elementUtils;
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        // 规定需要处理的注解
        Set<String> supportedAnnotationTypes = new LinkedHashSet<>();
        supportedAnnotationTypes.add(ModuleEntry.class.getCanonicalName());
        return supportedAnnotationTypes;
    }


    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        System.out.println("=================");
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(ModuleEntry.class);
        List<String> lists = new ArrayList<>();
        for (Element element : elements) {
            // 判断是否Class
            TypeElement typeElement = (TypeElement) element;
            lists.add(typeElement.getQualifiedName().toString());
        }
        if (lists.size() > 0) {
            OutputStream outputStream = null;
            try {
                FileObject resource = processingEnv.getFiler().createResource(StandardLocation.CLASS_OUTPUT, "","META-INF/services/github.hotstu.ezuniplugin.base.ILoader");
                outputStream = resource.openOutputStream();
                BufferedWriter outputStreamWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
                for (int i = 0; i < lists.size(); i++) {
                    if (i > 0) {
                        outputStreamWriter.newLine();
                    }
                    outputStreamWriter.write(lists.get(i));
                }
                outputStreamWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (IOException e) {
                    }
                }
            }
        }

        return true;
    }


    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        elementUtils = processingEnv.getElementUtils();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_7;
    }

}