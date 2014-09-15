package com.jcandksolutions.gradle.androidunittest

import com.android.build.gradle.BasePlugin
import com.android.build.gradle.api.BaseVariant
import com.android.builder.model.ArtifactMetaData
import com.android.builder.model.SourceProvider

import org.gradle.api.artifacts.Configuration
import org.gradle.api.tasks.SourceSet
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor

import static org.fest.assertions.api.Assertions.assertThat
import static org.mockito.Matchers.isNull
import static org.mockito.Mockito.eq
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.verify
import static org.mockito.Mockito.when

public class ModelManagerTest {
  private ModelManager mTarget
  private BasePlugin mPlugin
  private MockProvider mProvider

  @Before
  public void setUp() {
    mProvider = new MockProvider()
    mPlugin = mProvider.provideAndroidPlugin()
    mTarget = new ModelManager(mPlugin)
  }

  @Test
  public void testRegister() {
    mTarget.register()
    verify(mPlugin).registerArtifactType("_unit_test_", true, ArtifactMetaData.TYPE_JAVA)
    verify(mPlugin).registerArtifactType("_sources_javadoc_", true, ArtifactMetaData.TYPE_JAVA)
  }

  @Test
  public void testRegisterArtifact() {
    VariantWrapper variant = mock(VariantWrapper.class)
    BaseVariant baseVariant = mock(BaseVariant.class)
    when(variant.baseVariant).thenReturn(baseVariant)
    SourceSet sourceSet = mock(SourceSet.class)
    when(variant.sourceSet).thenReturn(sourceSet)
    String javaCompileTaskName = "javaCompileTaskName"
    when(sourceSet.compileJavaTaskName).thenReturn(javaCompileTaskName)
    Configuration configuration = mock(Configuration.class)
    when(variant.configuration).thenReturn(configuration)
    File classesFolder = new File("classes")
    when(variant.compileDestinationDir).thenReturn(classesFolder)
    mTarget.registerArtifact(variant)
    ArgumentCaptor<TestSourceProvider> captor = ArgumentCaptor.forClass(TestSourceProvider.class)
    verify(mPlugin).registerJavaArtifact(eq("_unit_test_"), eq(baseVariant), eq(javaCompileTaskName), eq(javaCompileTaskName), eq(configuration), eq(classesFolder), captor.capture())
    assertThat(captor.value).isExactlyInstanceOf(TestSourceProvider.class)
  }

  @Test
  public void testRegisterJavadocSourcesArtifact() throws Exception {
    Configuration config = mock(Configuration.class)
    mTarget.registerJavadocSourcesArtifact(config)
    ArgumentCaptor<File> fileCaptor = ArgumentCaptor.forClass(File.class)
    ArgumentCaptor<BaseVariant> variantCaptor = ArgumentCaptor.forClass(BaseVariant.class)
    verify(mPlugin).registerJavaArtifact(eq("_sources_javadoc_"), variantCaptor.capture(), eq("dummyAssembleTaskName"), eq("dummyJavaCompileTaskName"), eq(config), fileCaptor.capture(), isNull(SourceProvider.class))
    assertThat(fileCaptor.value).isEqualTo(new File("dummyClassesFolder"))
    BaseVariant variant = variantCaptor.value
    assertThat(variant.name).isEqualTo("SourcesJavadoc")
    assertThat(variant.description).isNull()
    assertThat(variant.dirName).isNull()
    assertThat(variant.baseName).isNull()
    assertThat(variant.flavorName).isNull()
    assertThat(variant.buildType).isNull()
    assertThat(variant.mergedFlavor).isNull()
    assertThat(variant.productFlavors).isNull()
    assertThat(variant.sourceSets).isNull()
    assertThat(variant.outputFile).isNull()
    assertThat(variant.packageName).isNull()
    assertThat(variant.preBuild).isNull()
    assertThat(variant.checkManifest).isNull()
    assertThat(variant.processManifest).isNull()
    assertThat(variant.aidlCompile).isNull()
    assertThat(variant.renderscriptCompile).isNull()
    assertThat(variant.mergeResources).isNull()
    assertThat(variant.mergeAssets).isNull()
    assertThat(variant.processResources).isNull()
    assertThat(variant.generateBuildConfig).isNull()
    assertThat(variant.javaCompile).isNull()
    assertThat(variant.ndkCompile).isNull()
    assertThat(variant.obfuscation).isNull()
    assertThat(variant.processJavaResources).isNull()
    assertThat(variant.assemble).isNull()
  }
}
