/**
 * Created by gosc on 06.07.2016.
 */
public class MyPit {


//
//    private MutationAnalysisExecutor mae;
//    private Configuration config;
//
//    private MetaDataExtractor        metaDataExtractor;
//
//    //@Before
//    public void setUp() {
//       // MockitoAnnotations.initMocks(this);
//        this.config = new ConfigurationForTesting();
//        this.metaDataExtractor = new MetaDataExtractor();
//        this.mae = new MutationAnalysisExecutor(1, Collections.<MutationResultListener> singletonList(this.metaDataExtractor));
//    }
//
//    public static class NoMutations {
//
//    }
//
//    public static class OneMutationOnly {
//        public static int returnOne() {
//            return 1;
//        }
//    }
//
//    public static class ThreeMutations {
//        public static int returnOne() {
//            return 1;
//        }
//
//        public static int returnTwo() {
//            return 2;
//        }
//
//        public static int returnThree() {
//            return 3;
//        }
//    }
//
//    public static class OneMutationFullTest {
//
//    }
//
//
//    public static class ThreeMutationsTwoMeaningfullTests {
//
//    }
//
//
//    public void shouldDetectedMixOfSurvivingAndKilledMutations() {
//        run(ThreeMutations.class, ThreeMutationsTwoMeaningfullTests.class,
//                Mutator.byName("RETURN_VALS"));
//        verifyResults(SURVIVED, KILLED, KILLED);
//    }//dupaaaaaaaaa
//
//
//
//
//
//
//    private void run(final Class<?> clazz, final Class<?> test,
//                     final Collection<? extends MethodMutatorFactory> mutators) {
//
//        final ReportOptions data = new ReportOptions();
//
//        final Set<Predicate<String>> tests = Collections.singleton(Prelude
//                .isEqualTo(test.getName()));
//        data.setTargetTests(tests);
//        data.setDependencyAnalysisMaxDistance(-1);
//
//        final Set<Predicate<String>> mutees = Collections.singleton(Functions
//                .startsWith(clazz.getName()));
//        data.setTargetClasses(mutees);
//
//        data.setTimeoutConstant(PercentAndConstantTimeoutStrategy.DEFAULT_CONSTANT);
//        data.setTimeoutFactor(PercentAndConstantTimeoutStrategy.DEFAULT_FACTOR);
//
//        final JavaAgent agent = new JarCreatingJarFinder();
//
//        try {
//            createEngineAndRun(data, agent, mutators);
//        } finally {
//            agent.close();
//        }
//    }
//
//    private void createEngineAndRun(final ReportOptions data,
//                                    final JavaAgent agent,
//                                    final Collection<? extends MethodMutatorFactory> mutators) {
//
//        // data.setConfiguration(this.config);
//        final CoverageOptions coverageOptions = createCoverageOptions(data);
//
//        final LaunchOptions launchOptions = new LaunchOptions(agent,
//                new DefaultJavaExecutableLocator(), data.getJvmArgs(),
//                new HashMap<String, String>());
//
//        final PathFilter pf = new PathFilter(
//                Prelude.not(new DefaultDependencyPathPredicate()),
//                Prelude.not(new DefaultDependencyPathPredicate()));
//        final ProjectClassPaths cps = new ProjectClassPaths(data.getClassPath(),
//                data.createClassesFilter(), pf);
//
//        final Timings timings = new Timings();
//        final CodeSource code = new CodeSource(cps, coverageOptions.getPitConfig()
//                .testClassIdentifier());
//
//        final CoverageGenerator coverageGenerator = new DefaultCoverageGenerator(
//                null, coverageOptions, launchOptions, code, new NullCoverageExporter(),
//                timings, false);
//
//        final CoverageDatabase coverageData = coverageGenerator.calculateCoverage();
//
//        final Collection<ClassName> codeClasses = FCollection.map(code.getCode(),
//                ClassInfo.toClassName());
//
//        final MutationEngine engine = new GregorEngineFactory()
//                .createEngineWithMutators(false, False.<String> instance(),
//                        Collections.<String> emptyList(), mutators, true);
//
//        final MutationConfig mutationConfig = new MutationConfig(engine,
//                launchOptions);
//
//        final ClassloaderByteArraySource bas = new ClassloaderByteArraySource(
//                IsolationUtils.getContextClassLoader());
//        final MutationSource source = new MutationSource(mutationConfig,
//                UnfilteredMutationFilter.INSTANCE, new DefaultTestPrioritiser(
//                coverageData), bas);
//
//        final WorkerFactory wf = new WorkerFactory(null,
//                coverageOptions.getPitConfig(), mutationConfig,
//                new PercentAndConstantTimeoutStrategy(data.getTimeoutFactor(),
//                        data.getTimeoutConstant()), data.isVerbose(), data.getClassPath()
//                .getLocalClassPath());
//
//        final MutationTestBuilder builder = new MutationTestBuilder(wf,
//                new NullAnalyser(), source, new DefaultGrouper(0));
//
//        final List<MutationAnalysisUnit> tus = builder
//                .createMutationTestUnits(codeClasses);
//
//        this.mae.run(tus);
//    }
//
//    private CoverageOptions createCoverageOptions(ReportOptions data) {
//        return new CoverageOptions(data.getTargetClassesFilter(), this.config,
//                data.isVerbose(), data.getDependencyAnalysisMaxDistance());
//    }
//
//    protected void verifyResults(final DetectionStatus... detectionStatus) {
//       // final List<DetectionStatus> expected = Arrays.asList(detectionStatus);
//        final List<DetectionStatus> actual = this.metaDataExtractor.getDetectionStatus();
//
//       // Collections.sort(expected);
//        Collections.sort(actual);
//
//       // assertEquals(expected, actual);
//    }



}
