package com.jcandksolutions.gradle.androidunittest

import com.android.annotations.NonNull
import com.android.build.gradle.BasePlugin
import com.android.build.gradle.api.BaseVariant
import com.android.build.gradle.tasks.AidlCompile
import com.android.build.gradle.tasks.GenerateBuildConfig
import com.android.build.gradle.tasks.ManifestProcessorTask
import com.android.build.gradle.tasks.MergeAssets
import com.android.build.gradle.tasks.MergeResources
import com.android.build.gradle.tasks.NdkCompile
import com.android.build.gradle.tasks.ProcessAndroidResources
import com.android.build.gradle.tasks.RenderscriptCompile
import com.android.builder.core.DefaultBuildType
import com.android.builder.core.DefaultProductFlavor
import com.android.builder.model.ArtifactMetaData
import com.android.builder.model.SourceProvider

import org.gradle.api.Task
import org.gradle.api.artifacts.Configuration
import org.gradle.api.tasks.Copy
import org.gradle.api.tasks.compile.JavaCompile

/**
 * Class that handles the Modeling of the tests so the IDE can import it correctly.
 */
public class ModelManager {
  private static final String TEST_ARTIFACT_NAME = "_unit_test_"
  private static final String SOURCES_JAVADOC_ARTIFACT_NAME = "_sources_javadoc_"
  private final BasePlugin mAndroidPlugin
  /**
   * Instantiates a ModelManager.
   * @param androidPlugin The AndroidPlugin.
   */
  public ModelManager(BasePlugin androidPlugin) {
    mAndroidPlugin = androidPlugin
  }

  /**
   * Registers with the Android plugin that there is a test ArtifactType of pure Java type.
   */
  public void register() {
    mAndroidPlugin.registerArtifactType(TEST_ARTIFACT_NAME, true, ArtifactMetaData.TYPE_JAVA)
    mAndroidPlugin.registerArtifactType(SOURCES_JAVADOC_ARTIFACT_NAME, true, ArtifactMetaData.TYPE_JAVA)
  }

  /**
   * Registers under the ArtifactType registered in {@link #register()} the artifact generated for
   * the variant. Including the base Android variant it was generated from, the test compilation
   * task name, the test configuration, the test compile destination dir and a testSourceProvider.
   * @param variantWrapper The wrapper for the variant we generated tests for.
   */
  public void registerArtifact(VariantWrapper variantWrapper) {
    mAndroidPlugin.registerJavaArtifact(TEST_ARTIFACT_NAME,
        variantWrapper.baseVariant,
        variantWrapper.sourceSet.compileJavaTaskName,
        variantWrapper.sourceSet.compileJavaTaskName,
        variantWrapper.configuration,
        variantWrapper.compileDestinationDir,
        new TestSourceProvider(variantWrapper))
  }

  public void registerJavadocSourcesArtifact(Configuration configuration) {
    final BaseVariant variant = new BaseVariant() {
      @Override
      String getName() {
        return "SourcesJavadoc"
      }

      @Override
      String getDescription() {
        return null
      }

      @Override
      String getDirName() {
        return null
      }

      @Override
      String getBaseName() {
        return null
      }

      @Override
      String getFlavorName() {
        return null
      }

      @Override
      DefaultBuildType getBuildType() {
        return null
      }

      @Override
      DefaultProductFlavor getMergedFlavor() {
        return null
      }

      @Override
      List<DefaultProductFlavor> getProductFlavors() {
        return null
      }

      @Override
      List<SourceProvider> getSourceSets() {
        return null
      }

      @Override
      File getOutputFile() {
        return null
      }

      @Override
      void setOutputFile(@NonNull final File outputFile) {
      }

      @Override
      String getPackageName() {
        return null
      }

      @Override
      Task getPreBuild() {
        return null
      }

      @Override
      Task getCheckManifest() {
        return null
      }

      @Override
      ManifestProcessorTask getProcessManifest() {
        return null
      }

      @Override
      AidlCompile getAidlCompile() {
        return null
      }

      @Override
      RenderscriptCompile getRenderscriptCompile() {
        return null
      }

      @Override
      MergeResources getMergeResources() {
        return null
      }

      @Override
      MergeAssets getMergeAssets() {
        return null
      }

      @Override
      ProcessAndroidResources getProcessResources() {
        return null
      }

      @Override
      GenerateBuildConfig getGenerateBuildConfig() {
        return null
      }

      @Override
      JavaCompile getJavaCompile() {
        return null
      }

      @Override
      NdkCompile getNdkCompile() {
        return null
      }

      @Override
      Task getObfuscation() {
        return null
      }

      @Override
      Copy getProcessJavaResources() {
        return null
      }

      @Override
      Task getAssemble() {
        return null
      }

      @Override
      void addJavaSourceFoldersToModel(@NonNull final File... sourceFolders) {
      }

      @Override
      void addJavaSourceFoldersToModel(@NonNull final Collection<File> sourceFolders) {
      }

      @Override
      void registerJavaGeneratingTask(
          @NonNull final Task task, @NonNull final File... sourceFolders) {
      }

      @Override
      void registerJavaGeneratingTask(
          @NonNull final Task task, @NonNull final Collection<File> sourceFolders) {
      }
    }
    mAndroidPlugin.registerJavaArtifact(SOURCES_JAVADOC_ARTIFACT_NAME,
        variant,
        "dummyAssembleTaskName",
        "dummyJavaCompileTaskName",
        configuration,
        new File("dummyClassesFolder"),
        null)
  }
}
