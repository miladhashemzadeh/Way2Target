package com.vampyreworld.w2t.sharedui.localization

import androidx.compose.runtime.staticCompositionLocalOf


interface AppStrings {
    val morningGreeting: String
    val readyForGoals: String
    val masterGoals: String
    val todayActions: String
    val noMasterGoals: String
    val defineVision: String
    val breakdownPath: String
    val masterGoal: String
    val masterGoalDesc: String
    val milestones: String
    val milestonesDesc: String
    val actions: String
    val actionsDesc: String
    val createMasterGoal: String
    val allCaughtUp: String
    val noActionsToday: String
    val aiInsights: String
    val checkMood: String
    val howAreYouFeeling: String
    val moodCheckDesc: String
    val onboardingTitle1: String
    val onboardingTitle2: String
    val onboardingTitle3: String
    val onboardingSub1: String
    val onboardingSub2: String
    val onboardingSub3: String
    val onboardingMasterGoal: String
    val onboardingMasterGoalDesc: String
    val onboardingMilestone: String
    val onboardingMilestoneDesc: String
    val onboardingAction: String
    val onboardingActionDesc: String
    val onboardingChallenge: String
    val onboardingChallengeDesc: String
    val onboardingSolution: String
    val onboardingSolutionDesc: String
    val onboardingAiInsights: String
    val onboardingAiInsightsDesc: String
    val onboardingMood: String
    val onboardingMoodDesc: String
    val skip: String
    val next: String
    val getStarted: String
    val home: String
    val profile: String
    val challenges: String
    val ongoing: String
    val finished: String
    val failed: String
    val noChallenges: String
    val userProfileTitle: String
    val edit: String
    val dearUser: String
    val profileSubtitle: String
    val cancel: String
    val saveChanges: String
    val editProfile: String
    val name: String
    val age: String
    val profilePictureUrl: String
    val mentalTypeLabel: String
    val worldviewLabel: String
    val notRegistered: String
    val mentalAnalytical: String
    val mentalCreative: String
    val mentalDriven: String
    val mentalStrategic: String
    val mentalEmpathetic: String
    val worldviewStoic: String
    val worldviewOptimist: String
    val worldviewRealist: String
    val worldviewPragmatic: String
    val worldviewIdealist: String
    val unknown: String
    val appraisalTitle: String
    val appraisingChallenge: String
    val appraisingTarget: String
    val startAiAppraisal: String
    val quickCheckIn: String
    val howsYourMind: String
    val alreadyCheckedIn: String
    val checkInTomorrow: String
    val energyLevel: String
    val focusConcentration: String
    val saveContinue: String
    val doThisLater: String
    val settings: String
    val darkMode: String
    val goBack: String
    val language: String
    val english: String
    val farsi: String
    val decisionAnalysis: String
    val decisionDesc: String
    val howItWorks: String
    val defineDilemma: String
    val defineDilemmaDesc: String
    val weighOptions: String
    val weighOptionsDesc: String
    val aiStrategy: String
    val aiStrategyDesc: String
    val startDecision: String
    val solutions: String
    val newSolution: String
    val defineStrategy: String
    val solutionDetails: String
    val solutionTitlePlaceholder: String
    val customizeCosts: String
    val hideCosts: String
    val detailedDescription: String
    val strategyType: String
    val strategicStrength: String
    val estimatedCosts: String
    val energy: String
    val time: String
    val money: String
    val saveSolution: String
    val understandingSolutions: String
    val formulateStrategies: String
    val formulateStrategiesDesc: String
    val balanceCosts: String
    val balanceCostsDesc: String
    val existingSolutions: String

    // Missing Keys
    val deleteMasterGoalTitle: String
    val deleteMasterGoalConfirm: String
    val deleteGoalTypeToConfirm: String
    val deleteGoalPlaceholder: String
    val deletePermanently: String
    val deleteMilestone: String
    val deleteAction: String
    val deleteGoal: String
    val fromYourMilestones: String
    val percentComplete: String
    val viewStrategy: String
    val appraiseGoal: String
    val goalSummary: String
    val goalSummaryPrompt: String
    val yourReflection: String
    val reflectionPlaceholder: String
    val markAsComplete: String
    val archiveGoal: String
    val updateStatus: String
    val selectPreferredSolution: String
    val solutionPrompt: String
    val userLabel: String
    val updateChallenge: String
    val goalLabel: String
    val pending: String
    val addNewChallenge: String
    val challengeTitle: String
    val challengeTitlePlaceholder: String
    val challengeDescriptionPlaceholder: String
    val linkToGoal: String
    val selectAGoal: String
    val impactLevel: String
    val highImpact: String
    val mediumImpact: String
    val lowImpact: String
    val createChallenge: String
    val addNewSolution: String
    val selectSource: String
    val myIdea: String
    val ai: String
    val external: String
    val solutionDescription: String
    val solutionDescriptionPlaceholder: String
    val addSolution: String
    val aiSolutionNote: String
    val externalSolutionNote: String
    val solTypeAvoidance: String
    val solTypeDirectConfrontation: String
    val solTypeEditStructural: String
    val solTypeDividingAndConquer: String
    val solTypeSubstitution: String
    val solTypeDelegate: String
    val solTypePlanning: String
    val solTypeEmotionalRegulation: String
    val solTypeHelp: String
    val solTypeTryAndFail: String
    val createActionGoal: String
    val goalTitle: String
    val actionTitlePlaceholder: String
    val scheduleType: String
    val scheduleOnce: String
    val scheduleRecurring: String
    val scheduleHabit: String
    val taskTiming: String
    val startsOn: String
    val startsOnDate: String
    val startDateOptional: String
    val startDateImmediately: String
    val deadlineText: String
    val deadlineDate: String
    val reminderBefore: String
    val repeatSettings: String
    val daily: String
    val weekly: String
    val startingAt: String
    val endsOn: String
    val endDateOptional: String
    val notifyMeBefore: String
    val daysOfWeek: String
    val habitStrategy: String
    val sessionDuration: String
    val durationPlaceholder: String
    val frequencyXDays: String
    val frequencyPlaceholder: String
    val commitmentWeeks: String
    val weeksPlaceholder: String
    val hideOptionalDetails: String
    val customizeDescriptionCosts: String
    val howWillYouDoThis: String
    val completionCriteria: String
    val completionCriteriaPlaceholder: String
    val selectIcon: String
    val isThisLongTermAmbition: String
    val lifeGoalLabel: String
    val customizeIconDescription: String
    val whatDoesAchievingMean: String
    val aboutUs: String
    val aboutUsScreenGoBack: String
    val titleLabel: String
    val save: String
    val createChallenges: String
    val goalBreakdown: String
    val masterGoalPrefix: String
    val milestonePrefix: String
    val activeChallenges: String
    val addMilestone: String
    val skillMilestone: String
    val conferNewSkill: String
    val whatIsMilestoneAbout: String
    val createChallengeForGoal: String
    val actionGoalsCount: String
    val noActionsDefined: String
    val completed: String
    val dueTomorrow: String
    val activeChallengesCount: String
    val addAction: String
    val dueDate: String
    val tomorrow5pm: String
    val focus5dayStreak: String
    val description: String
    val createMilestoneGoal: String
    val milestoneTitlePlaceholder: String
    val milestoneSuggestMvp: String
    val milestoneSuggestBasics: String
    val milestoneSuggestSale: String
    val milestoneSuggestTeam: String
    val milestoneSuggestBeta: String
    val milestoneSuggestFit: String
    val parentGoalPrefix: String
    val details: String
    val stabilityConditions: String
    val suggestedSolutions: String
    val viewAll: String
    val noSolutionsFound: String
    val activeChallenge: String
    val noDescriptionChallenge: String
    val aiHelp: String
    val solution: String
    val decide: String
    val deleteChallenge: String
    val percentFormat: String
    val defaultStrategyRecommendation: String

    // Presets & Days Short
    val daysShort: List<String>
    val presetWorkoutLabel: String
    val presetWorkoutTitle: String
    val presetWorkoutDesc: String
    val presetWorkoutCriteria: String
    val presetCodeLabel: String
    val presetCodeTitle: String
    val presetCodeDesc: String
    val presetCodeCriteria: String
    val presetReadLabel: String
    val presetReadTitle: String
    val presetReadDesc: String
    val presetReadCriteria: String
    val presetMeditateLabel: String
    val presetMeditateTitle: String
    val presetMeditateDesc: String
    val presetMeditateCriteria: String
    val presetReviewLabel: String
    val presetReviewTitle: String
    val presetReviewDesc: String
    val presetReviewCriteria: String
    val presetGroceriesLabel: String
    val presetGroceriesTitle: String
    val presetGroceriesDesc: String
    val presetGroceriesCriteria: String
}

object EnStrings : AppStrings {
    override val morningGreeting = "Good morning"
    override val readyForGoals = "Are you ready to achieve your goals?"
    override val masterGoals = "Your Master Goals"
    override val todayActions = "Today's Actions"
    override val noMasterGoals = "No master goals yet. Start by creating one!"
    override val defineVision = "Define Your Vision"
    override val breakdownPath = "To target your destination, break down your path into simple steps:"
    override val masterGoal = "Master Goal"
    override val masterGoalDesc = "Define your ultimate destination or vision (e.g., Learn Compose)."
    override val milestones = "Milestones"
    override val milestonesDesc = "Break your goal down into clear roadmap milestones."
    override val actions = "Actions"
    override val actionsDesc = "Create bite-sized, actionable tasks for your daily routine."
    override val createMasterGoal = "Create Master Goal"
    override val allCaughtUp = "All Caught Up!"
    override val noActionsToday = "No daily actions scheduled for today. Goals only progress when you assign today's tasks inside your milestones."
    override val aiInsights = "AI Insights"
    override val checkMood = "Check Mood"
    override val howAreYouFeeling = "How are you feeling?"
    override val moodCheckDesc = "Quick check to optimize your decision-making."
    override val onboardingTitle1 = "Structure Your Ambition"
    override val onboardingTitle2 = "Overcome Any Obstacle"
    override val onboardingTitle3 = "Optimize Your Performance"
    override val onboardingSub1 = "Break down big dreams into achievable steps."
    override val onboardingSub2 = "Turn blockers into breakthroughs with a powerful challenge system."
    override val onboardingSub3 = "Use AI-driven insights and mood tracking to reach your peak potential."
    override val onboardingMasterGoal = "Master Goal"
    override val onboardingMasterGoalDesc = "Your long-term strategic vision."
    override val onboardingMilestone = "Milestone Goal"
    override val onboardingMilestoneDesc = "Measurable checkpoints on your path."
    override val onboardingAction = "Action Goal"
    override val onboardingActionDesc = "Small, executable daily tasks with reminders."
    override val onboardingChallenge = "Challenge System"
    override val onboardingChallengeDesc = "Create challenges when you feel blocked on a goal."
    override val onboardingSolution = "Solution Engine"
    override val onboardingSolutionDesc = "Explore multiple solutions, powered by AI."
    override val onboardingAiInsights = "AI Insights"
    override val onboardingAiInsightsDesc = "Get smart recommendations based on your progress."
    override val onboardingMood = "Mood Stability"
    override val onboardingMoodDesc = "Track your mental state to optimize decisions."
    override val skip = "Skip"
    override val next = "Next"
    override val getStarted = "Get Started"
    override val home = "Home"
    override val profile = "Profile"
    override val challenges = "Challenges"
    override val ongoing = "Ongoing"
    override val finished = "Finished"
    override val failed = "Failed"
    override val noChallenges = "No challenges found in this category."
    override val userProfileTitle = "User Profile"
    override val edit = "Edit"
    override val dearUser = "Dear User"
    override val profileSubtitle = "Personal info to customize your experience"
    override val cancel = "Cancel"
    override val saveChanges = "Save Changes"
    override val editProfile = "Edit Profile"
    override val name = "Name"
    override val age = "Age"
    override val profilePictureUrl = "Profile Picture (URL)"
    override val mentalTypeLabel = "Mental Type"
    override val worldviewLabel = "Worldview"
    override val notRegistered = "Not Registered"
    override val mentalAnalytical = "Analytical"
    override val mentalCreative = "Creative"
    override val mentalDriven = "Driven"
    override val mentalStrategic = "Strategic"
    override val mentalEmpathetic = "Empathetic"
    override val worldviewStoic = "Stoicism"
    override val worldviewOptimist = "Optimist"
    override val worldviewRealist = "Realist"
    override val worldviewPragmatic = "Pragmatic"
    override val worldviewIdealist = "Idealist"
    override val unknown = "Unknown"
    override val appraisalTitle = "Appraisal"
    override val appraisingChallenge = "Appraising Challenge"
    override val appraisingTarget = "Appraising Target"
    override val startAiAppraisal = "Start AI Appraisal"
    override val quickCheckIn = "Quick Check-in"
    override val howsYourMind = "How's your current state of mind?"
    override val alreadyCheckedIn = "You've already checked in today!"
    override val checkInTomorrow = "Come back tomorrow for your next check-in."
    override val energyLevel = "Energy Level"
    override val focusConcentration = "Focus & Concentration"
    override val saveContinue = "Save & Continue"
    override val doThisLater = "I'll do this later"
    override val settings = "Settings"
    override val darkMode = "Dark Mode"
    override val goBack = "Go Back"
    override val language = "Language"
    override val english = "English"
    override val farsi = "فارسی (Farsi)"
    override val decisionAnalysis = "Decision Analysis"
    override val decisionDesc = "Navigate complex choices with confidence using structured logic and AI insights."
    override val howItWorks = "How it works:"
    override val defineDilemma = "1. Define the Dilemma"
    override val defineDilemmaDesc = "Clearly formulate the choice you need to make or options you want to weigh."
    override val weighOptions = "2. Weigh the Options"
    override val weighOptionsDesc = "Identify pros, cons, and custom weight values for each alternative solution."
    override val aiStrategy = "3. AI Strategy Recommendations"
    override val aiStrategyDesc = "Receive weighted scores and strategic guidance to target the best possible path."
    override val startDecision = "Start Decision Analysis"
    override val solutions = "Solutions"
    override val newSolution = "New Solution"
    override val defineStrategy = "Define a strategy to overcome your challenge"
    override val solutionDetails = "Solution Details"
    override val solutionTitlePlaceholder = "Solution title (e.g. Set a strict timer)..."
    override val customizeCosts = "Customize Costs & Strategy Details 🔽"
    override val hideCosts = "Hide Costs & Strategy Details 🔼"
    override val detailedDescription = "Detailed description (optional)..."
    override val strategyType = "Strategy Type"
    override val strategicStrength = "Strategic Strength"
    override val estimatedCosts = "Estimated Costs (0-100 scale)"
    override val energy = "Energy"
    override val time = "Time"
    override val money = "Money"
    override val saveSolution = "Save Solution"
    override val understandingSolutions = "Understanding Solutions"
    override val formulateStrategies = "Formulate Strategies"
    override val formulateStrategiesDesc = "Draft actionable solutions to bypass constraints or hurdles blockading your targets."
    override val balanceCosts = "Balance the Costs"
    override val balanceCostsDesc = "Optimize cost indicators (Energy, Time, Money) to make sustainable and realistic progress."
    override val existingSolutions = "Existing Solutions"

    // Missing Keys Implementations (English)
    override val deleteMasterGoalTitle = "Delete Master Goal?"
    override val deleteMasterGoalConfirm = "This action cannot be undone. All milestones and actions will be lost."
    override val deleteGoalTypeToConfirm = "Type 'Goal #%s' to confirm"
    override val deleteGoalPlaceholder = "Goal #%s"
    override val deletePermanently = "Delete Permanently"
    override val deleteMilestone = "Delete Milestone"
    override val deleteAction = "Delete Action"
    override val deleteGoal = "Delete Goal"
    override val fromYourMilestones = "From your milestones"
    override val percentComplete = "%d%% Complete"
    override val viewStrategy = "View Strategy"
    override val appraiseGoal = "Appraise Goal"
    override val goalSummary = "Goal Summary"
    override val goalSummaryPrompt = "You've made significant progress. Challenges encountered have been mostly resolved. Keep up the momentum!"
    override val yourReflection = "Your Reflection"
    override val reflectionPlaceholder = "What did you learn? What went well or poorly?"
    override val markAsComplete = "Mark as Complete"
    override val archiveGoal = "Archive Goal"
    override val updateStatus = "Update Status"
    override val selectPreferredSolution = "Select Preferred Solution"
    override val solutionPrompt = "Which solution are you actively pursuing or found most helpful?"
    override val userLabel = "(User)"
    override val updateChallenge = "Update Challenge"
    override val goalLabel = "Goal: %s"
    override val pending = "Pending"
    override val addNewChallenge = "Add New Challenge"
    override val challengeTitle = "Challenge Title"
    override val challengeTitlePlaceholder = "e.g., Stuck on complex data structures"
    override val challengeDescriptionPlaceholder = "Describe the blocker or problem you're facing."
    override val linkToGoal = "Link to Goal"
    override val selectAGoal = "Select a Goal"
    override val impactLevel = "Impact Level"
    override val highImpact = "High Impact"
    override val mediumImpact = "Medium Impact"
    override val lowImpact = "Low Impact"
    override val createChallenge = "Create Challenge"
    override val addNewSolution = "Add New Solution"
    override val selectSource = "Select Source"
    override val myIdea = "My Idea"
    override val ai = "AI"
    override val external = "External"
    override val solutionDescription = "Solution Description"
    override val solutionDescriptionPlaceholder = "Suggest a new approach, resource, or strategy."
    override val addSolution = "Add Solution"
    override val aiSolutionNote = "AI solutions are automatically generated based on the challenge."
    override val externalSolutionNote = "External resources integration coming soon."
    override val solTypeAvoidance = "Avoidance"
    override val solTypeDirectConfrontation = "Direct Confrontation"
    override val solTypeEditStructural = "Edit Structural"
    override val solTypeDividingAndConquer = "Dividing & Conquer"
    override val solTypeSubstitution = "Substitution"
    override val solTypeDelegate = "Delegate"
    override val solTypePlanning = "Planning"
    override val solTypeEmotionalRegulation = "Emotional Regulation"
    override val solTypeHelp = "Help"
    override val solTypeTryAndFail = "Try & Fail"
    override val createActionGoal = "Create Action Goal"
    override val goalTitle = "Goal Title"
    override val actionTitlePlaceholder = "e.g., Study 30 min/day"
    override val scheduleType = "Schedule Type"
    override val scheduleOnce = "Once"
    override val scheduleRecurring = "Recurring"
    override val scheduleHabit = "Habit"
    override val taskTiming = "Task Timing"
    override val startsOn = "Starts: %s"
    override val startsOnDate = "Starts on: %s"
    override val startDateOptional = "Start Date (Optional)"
    override val startDateImmediately = "Start Date (Immediately)"
    override val deadlineText = "Deadline: %s"
    override val deadlineDate = "Deadline Date"
    override val reminderBefore = "Reminder (mins before)"
    override val repeatSettings = "Repeat Settings"
    override val daily = "Daily"
    override val weekly = "Weekly"
    override val startingAt = "Starting at: %s"
    override val endsOn = "Ends on: %s"
    override val endDateOptional = "End Date (Optional)"
    override val notifyMeBefore = "Notify me (mins before)"
    override val daysOfWeek = "Days of Week"
    override val habitStrategy = "Habit Strategy"
    override val sessionDuration = "Session Duration (mins)"
    override val durationPlaceholder = "e.g. 30"
    override val frequencyXDays = "Frequency (every X days)"
    override val frequencyPlaceholder = "1 for daily, 7 for weekly"
    override val commitmentWeeks = "Commitment (Total weeks)"
    override val weeksPlaceholder = "e.g. 12"
    override val hideOptionalDetails = "Hide Optional Details 🔼"
    override val customizeDescriptionCosts = "Customize Description & Costs 🔽"
    override val howWillYouDoThis = "How will you do this?"
    override val completionCriteria = "Completion Criteria"
    override val completionCriteriaPlaceholder = "What does 'done' look like?"
    override val selectIcon = "Select an Icon"
    override val isThisLongTermAmbition = "Is this a long-term life ambition?"
    override val lifeGoalLabel = "Life Goal"
    override val customizeIconDescription = "Customize Icon & Description 🔽"
    override val whatDoesAchievingMean = "What does achieving this goal mean to you?"
    override val aboutUs = "About Us"
    override val aboutUsScreenGoBack = "About Us Screen - Go Back"
    override val titleLabel = "Title"
    override val save = "Save"
    override val createChallenges = "Create Challenges"
    override val goalBreakdown = "Goal Breakdown"
    override val masterGoalPrefix = "Master Goal: %s"
    override val milestonePrefix = "Milestone: %s"
    override val activeChallenges = "Active Challenges"
    override val addMilestone = "Add Milestone"
    override val skillMilestone = "Skill Milestone"
    override val conferNewSkill = "Does completing this milestone confer a new skill?"
    override val whatIsMilestoneAbout = "What is this milestone about?"
    override val createChallengeForGoal = "Create challenge for this goal"
    override val actionGoalsCount = "Action Goals (%d)"
    override val noActionsDefined = "No actions defined for this milestone."
    override val completed = "Completed"
    override val dueTomorrow = "Due Tomorrow"
    override val activeChallengesCount = "Active Challenges (%d)"
    override val addAction = "Add Action"
    override val dueDate = "Due Date"
    override val tomorrow5pm = "Tomorrow, 5:00 PM"
    override val focus5dayStreak = "Focus on completing this task today to maintain your 5-day streak!"
    override val description = "Description"
    override val createMilestoneGoal = "Create Milestone Goal"
    override val milestoneTitlePlaceholder = "e.g., Reach German B1"
    override val milestoneSuggestMvp = "Build MVP 🚀"
    override val milestoneSuggestBasics = "Learn Basics 📚"
    override val milestoneSuggestSale = "First Sale 💰"
    override val milestoneSuggestTeam = "Hire Team 👥"
    override val milestoneSuggestBeta = "Launch Beta 📢"
    override val milestoneSuggestFit = "Get Fit 🏃"
    override val parentGoalPrefix = "Parent Goal ›"
    override val details = "Details"
    override val stabilityConditions = "Stability Conditions"
    override val suggestedSolutions = "Suggested Solutions"
    override val viewAll = "View All"
    override val noSolutionsFound = "No solutions found. Try AI Help or make a decision."
    override val activeChallenge = "Active Challenge"
    override val noDescriptionChallenge = "No description provided for this challenge."
    override val aiHelp = "AI Help"
    override val solution = "Solution"
    override val decide = "Decide"
    override val deleteChallenge = "Delete Challenge"
    override val percentFormat = "%d%%"
    override val defaultStrategyRecommendation = "Focus on one task at a time, implement basic operations, then build up to complex solutions."

    // Presets & Days Short (English)
    override val daysShort = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
    override val presetWorkoutLabel = "30m Workout 🏃"
    override val presetWorkoutTitle = "30m Workout"
    override val presetWorkoutDesc = "Get moving! Do some cardio or strength training."
    override val presetWorkoutCriteria = "Finish workout and log in fitness app"
    override val presetCodeLabel = "Code 1 Hour 💻"
    override val presetCodeTitle = "Code 1 Hour"
    override val presetCodeDesc = "Focus work on personal projects or tutorials."
    override val presetCodeCriteria = "Commit changes or finish 1 coding lesson"
    override val presetReadLabel = "Read 15 Mins 📚"
    override val presetReadTitle = "Read 15 Mins"
    override val presetReadDesc = "Read a non-fiction or educational book."
    override val presetReadCriteria = "Read at least 10 pages"
    override val presetMeditateLabel = "Meditate 10m 🧘‍♀️"
    override val presetMeditateTitle = "Meditate 10m"
    override val presetMeditateDesc = "Breathe in, breathe out. Keep focus on the present."
    override val presetMeditateCriteria = "Complete a 10 minute guided session"
    override val presetReviewLabel = "Weekly Review 📈"
    override val presetReviewTitle = "Weekly Review"
    override val presetReviewDesc = "Look back at this week's progress and schedule next week."
    override val presetReviewCriteria = "Review completed actions and milestones"
    override val presetGroceriesLabel = "Buy Groceries 🛒"
    override val presetGroceriesTitle = "Buy Groceries"
    override val presetGroceriesDesc = "Go to store and stock up on healthy ingredients."
    override val presetGroceriesCriteria = "Buy everything on shopping list"
}

object FaStrings : AppStrings {
    override val morningGreeting = "صبح بخیر"
    override val readyForGoals = "آماده‌ای برای رسیدن به اهدافت؟"
    override val masterGoals = "اهداف اصلی شما"
    override val todayActions = "اقدامات امروز"
    override val noMasterGoals = "هنوز هدف اصلی ندارید. با ایجاد یکی شروع کنید!"
    override val defineVision = "چشم‌انداز خود را تعریف کنید"
    override val breakdownPath = "برای رسیدن به مقصد، مسیر خود را به گام‌های ساده تقسیم کنید:"
    override val masterGoal = "هدف اصلی"
    override val masterGoalDesc = "مقصد نهایی یا چشم‌انداز خود را تعریف کنید (مثلاً یادگیری کامپوز)."
    override val milestones = "مراحل اصلی"
    override val milestonesDesc = "هدف خود را به مراحل واضح نقشه راه تقسیم کنید."
    override val actions = "اقدامات"
    override val actionsDesc = "کارهای کوچک و قابل اجرا برای برنامه روزانه خود ایجاد کنید."
    override val createMasterGoal = "ایجاد هدف اصلی"
    override val allCaughtUp = "همه کارها انجام شده!"
    override val noActionsToday = "هیچ اقدام روزانه‌ای برای امروز برنامه‌ریزی نشده است. اهداف فقط زمانی پیشرفت می‌کنند که کارهای امروز را در مراحل اصلی خود تعیین کنید."
    override val aiInsights = "بینش‌های هوش مصنوعی"
    override val checkMood = "بررسی وضعیت روحی"
    override val howAreYouFeeling = "چه احساسی داری؟"
    override val moodCheckDesc = "بررسی سریع برای بهینه‌سازی تصمیم‌گیری شما."
    override val onboardingTitle1 = "ساختاردهی به اهداف"
    override val onboardingTitle2 = "غلبه بر موانع"
    override val onboardingTitle3 = "بهینه‌سازی عملکرد"
    override val onboardingSub1 = "رویاهای بزرگ خود را به گام‌های دست‌یافتنی تقسیم کنید."
    override val onboardingSub2 = "با یک سیستم چالش‌های قدرتمند، موانع را به فرصت‌ها تبدیل کنید."
    override val onboardingSub3 = "برای رسیدن به اوج توانایی‌های خود، از بینش‌های هوش مصنوعی و پیگیری وضعیت روحی استفاده کنید."
    override val onboardingMasterGoal = "هدف اصلی"
    override val onboardingMasterGoalDesc = "چشم‌انداز استراتژیک بلندمدت شما."
    override val onboardingMilestone = "هدف مرحله‌ای"
    override val onboardingMilestoneDesc = "نقاط بررسی سنجش‌پذیر در مسیر شما."
    override val onboardingAction = "هدف اجرایی"
    override val onboardingActionDesc = "کارهای روزانه کوچک و قابل اجرا با یادآورها."
    override val onboardingChallenge = "سیستم چالش‌ها"
    override val onboardingChallengeDesc = "هنگامی که در دستیابی به هدفی مسدود شدید، چالش ایجاد کنید."
    override val onboardingSolution = "موتور راهکارها"
    override val onboardingSolutionDesc = "بررسی چندین راهکار مختلف، با قدرت هوش مصنوعی."
    override val onboardingAiInsights = "بینش‌های هوش مصنوعی"
    override val onboardingAiInsightsDesc = "دریافت توصیه‌های هوشمند بر اساس پیشرفت شما."
    override val onboardingMood = "ثبات روحی"
    override val onboardingMoodDesc = "پیگیری وضعیت ذهنی برای بهینه‌سازی تصمیمات."
    override val skip = "رد شدن"
    override val next = "بعدی"
    override val getStarted = "شروع کنید"
    override val home = "خانه"
    override val profile = "پروفایل"
    override val challenges = "چالش‌ها"
    override val ongoing = "در حال اجرا"
    override val finished = "پایان یافته"
    override val failed = "ناموفق"
    override val noChallenges = "هیچ چالشی در این دسته‌بندی پیدا نشد."
    override val userProfileTitle = "پروفایل کاربر"
    override val edit = "ویرایش"
    override val dearUser = "کاربر عزیز"
    override val profileSubtitle = "اطلاعات شخصی برای شخصی‌سازی تجربه شما"
    override val cancel = "انصراف"
    override val saveChanges = "ذخیره تغییرات"
    override val editProfile = "ویرایش پروفایل"
    override val name = "نام"
    override val age = "سن"
    override val profilePictureUrl = "آدرس تصویر پروفایل (URL)"
    override val mentalTypeLabel = "تایپ روحی"
    override val worldviewLabel = "نوع دیدگاه"
    override val notRegistered = "ثبت نشده"
    override val mentalAnalytical = "تحلیلی"
    override val mentalCreative = "خلاق"
    override val mentalDriven = "عملگرا"
    override val mentalStrategic = "استراتژیک"
    override val mentalEmpathetic = "همدل"
    override val worldviewStoic = "رواقی‌گری"
    override val worldviewOptimist = "خوش‌بین"
    override val worldviewRealist = "واقع‌بین"
    override val worldviewPragmatic = "عمل‌گرا"
    override val worldviewIdealist = "آرمان‌گرا"
    override val unknown = "نامشخص"
    override val appraisalTitle = "ارزیابی"
    override val appraisingChallenge = "ارزیابی چالش"
    override val appraisingTarget = "ارزیابی هدف"
    override val startAiAppraisal = "شروع ارزیابی هوش مصنوعی"
    override val quickCheckIn = "ثبت وضعیت سریع"
    override val howsYourMind = "وضعیت ذهنی فعلی شما چگونه است؟"
    override val alreadyCheckedIn = "شما امروز قبلاً ثبت وضعیت کرده‌اید!"
    override val checkInTomorrow = "فردا برای ثبت وضعیت بعدی خود بازگردید."
    override val energyLevel = "سطح انرژی"
    override val focusConcentration = "تمرکز و توجه"
    override val saveContinue = "ذخیره و ادامه"
    override val doThisLater = "بعداً این کار را انجام می‌دهم"
    override val settings = "تنظیمات"
    override val darkMode = "حالت تاریک"
    override val goBack = "بازگشت"
    override val language = "زبان"
    override val english = "English"
    override val farsi = "فارسی"
    override val decisionAnalysis = "تحلیل تصمیم‌گیری"
    override val decisionDesc = "با اطمینان و با استفاده از منطق ساختاریافته و بینش‌های هوش مصنوعی، گزینه‌های پیچیده را هدایت کنید."
    override val howItWorks = "نحوه کارکرد:"
    override val defineDilemma = "۱. تعریف مسئله"
    override val defineDilemmaDesc = "انتخابی که باید انجام دهید یا گزینه‌هایی که می‌خواهید بسنجید را به وضوح فرموله کنید."
    override val weighOptions = "۲. سنجش گزینه‌ها"
    override val weighOptionsDesc = "مزایا، معایب و ارزش‌های وزنی دلخواه را برای هر راهکار جایگزین مشخص کنید."
    override val aiStrategy = "۳. توصیه‌های استراتژیک هوش مصنوعی"
    override val aiStrategyDesc = "امتیازهای وزنی و راهنمایی‌های استراتژیک را برای تعیین بهترین مسیر ممکن دریافت کنید."
    override val startDecision = "شروع تحلیل تصمیم"
    override val solutions = "راهکارها"
    override val newSolution = "راهکار جدید"
    override val defineStrategy = "استراتژی‌ای برای غلبه بر چالش خود تعریف کنید"
    override val solutionDetails = "جزئیات راهکار"
    override val solutionTitlePlaceholder = "عنوان راهکار (مثلاً تنظیم یک تایمر دقیق)..."
    override val customizeCosts = "سفارشی‌سازی هزینه‌ها و جزئیات استراتژی 🔽"
    override val hideCosts = "پنهان‌سازی هزینه‌ها و جزئیات استراتژی 🔼"
    override val detailedDescription = "توضیحات دقیق (اختیاری)..."
    override val strategyType = "نوع استراتژی"
    override val strategicStrength = "قدرت استراتژیک"
    override val estimatedCosts = "هزینه‌های تخمینی (مقیاس ۰ تا ۱۰۰)"
    override val energy = "انرژی"
    override val time = "زمان"
    override val money = "پول"
    override val saveSolution = "ذخیره راهکار"
    override val understandingSolutions = "درک راهکارها"
    override val formulateStrategies = "فرموله‌سازی استراتژی‌ها"
    override val formulateStrategiesDesc = "راهکارهای عملی برای دور زدن محدودیت‌ها یا موانعی که اهداف شما را مسدود کرده‌اند، بنویسید."
    override val balanceCosts = "تعادل در هزینه‌ها"
    override val balanceCostsDesc = "شاخص‌های هزینه (انرژی، زمان، پول) را برای پیشرفت پایدار و واقعی بهینه کنید."
    override val existingSolutions = "راهکارهای موجود"

    // Missing Keys Implementations (Farsi)
    override val deleteMasterGoalTitle = "حذف هدف اصلی؟"
    override val deleteMasterGoalConfirm = "این عمل قابل بازگشت نیست. تمام مراحل و اقدامات از بین خواهند رفت."
    override val deleteGoalTypeToConfirm = "برای تأیید عبارت 'Goal #%s' را تایپ کنید"
    override val deleteGoalPlaceholder = "Goal #%s"
    override val deletePermanently = "حذف دائمی"
    override val deleteMilestone = "حذف مرحله"
    override val deleteAction = "حذف اقدام"
    override val deleteGoal = "حذف هدف"
    override val fromYourMilestones = "از مراحل اصلی شما"
    override val percentComplete = "%d٪ کامل شده"
    override val viewStrategy = "مشاهده استراتژی"
    override val appraiseGoal = "ارزیابی هدف"
    override val goalSummary = "خلاصه هدف"
    override val goalSummaryPrompt = "شما پیشرفت قابل توجهی داشته‌اید. چالش‌های پیش‌رو تا حد زیادی حل شده‌اند. به تلاش خود ادامه دهید!"
    override val yourReflection = "بازخورد شما"
    override val reflectionPlaceholder = "چه چیزی یاد گرفتید؟ چه کارهایی خوب یا بد پیش رفت؟"
    override val markAsComplete = "علامت‌گذاری به عنوان کامل شده"
    override val archiveGoal = "بایگانی هدف"
    override val updateStatus = "به‌روزرسانی وضعیت"
    override val selectPreferredSolution = "انتخاب راهکار ترجیحی"
    override val solutionPrompt = "کدام راهکار را به طور فعال دنبال می‌کنید یا برایتان مفیدتر بوده است؟"
    override val userLabel = "(کاربر)"
    override val updateChallenge = "به‌روزرسانی چالش"
    override val goalLabel = "هدف: %s"
    override val pending = "در انتظار"
    override val addNewChallenge = "افزودن چالش جدید"
    override val challengeTitle = "عنوان چالش"
    override val challengeTitlePlaceholder = "مثال: دشواری در درک ساختارهای داده پیچیده"
    override val challengeDescriptionPlaceholder = "مانع یا مشکلی را که با آن روبرو هستید توصیف کنید."
    override val linkToGoal = "اتصال به هدف"
    override val selectAGoal = "انتخاب یک هدف"
    override val impactLevel = "سطح تأثیر"
    override val highImpact = "تأثیر بالا"
    override val mediumImpact = "تأثیر متوسط"
    override val lowImpact = "تأثیر کم"
    override val createChallenge = "ایجاد چالش"
    override val addNewSolution = "افزودن راهکار جدید"
    override val selectSource = "انتخاب منبع"
    override val myIdea = "ایده من"
    override val ai = "هوش مصنوعی"
    override val external = "خارجی"
    override val solutionDescription = "توضیحات راهکار"
    override val solutionDescriptionPlaceholder = "یک رویکرد، منبع یا استراتژی جدید پیشنهاد کنید."
    override val addSolution = "افزودن راهکار"
    override val aiSolutionNote = "راهکارهای هوش مصنوعی به طور خودکار بر اساس چالش تولید می‌شوند."
    override val externalSolutionNote = "ادغام منابع خارجی به زودی ارائه می‌شود."
    override val solTypeAvoidance = "اجتناب"
    override val solTypeDirectConfrontation = "رویارویی مستقیم"
    override val solTypeEditStructural = "تغییر ساختاری"
    override val solTypeDividingAndConquer = "تقسیم و غلبه"
    override val solTypeSubstitution = "جایگزینی"
    override val solTypeDelegate = "تفویض اختیار"
    override val solTypePlanning = "برنامه‌ریزی"
    override val solTypeEmotionalRegulation = "تنظیم هیجانی"
    override val solTypeHelp = "درخواست کمک"
    override val solTypeTryAndFail = "آزمون و خطا"
    override val createActionGoal = "ایجاد هدف اجرایی"
    override val goalTitle = "عنوان هدف"
    override val actionTitlePlaceholder = "مثال: ۳۰ دقیقه مطالعه در روز"
    override val scheduleType = "نوع زمان‌بندی"
    override val scheduleOnce = "یک‌بار"
    override val scheduleRecurring = "تکرارشونده"
    override val scheduleHabit = "عادت"
    override val taskTiming = "زمان‌بندی کار"
    override val startsOn = "شروع: %s"
    override val startsOnDate = "شروع از: %s"
    override val startDateOptional = "تاریخ شروع (اختیاری)"
    override val startDateImmediately = "تاریخ شروع (بلافاصله)"
    override val deadlineText = "مهلت: %s"
    override val deadlineDate = "تاریخ مهلت"
    override val reminderBefore = "یادآور (دقیقه قبل)"
    override val repeatSettings = "تنظیمات تکرار"
    override val daily = "روزانه"
    override val weekly = "هفتگی"
    override val startingAt = "شروع در ساعت: %s"
    override val endsOn = "پایان در: %s"
    override val endDateOptional = "تاریخ پایان (اختیاری)"
    override val notifyMeBefore = "اطلاع‌رسانی به من (دقیقه قبل)"
    override val daysOfWeek = "روزهای هفته"
    override val habitStrategy = "استراتژی عادت"
    override val sessionDuration = "مدت زمان جلسه (دقیقه)"
    override val durationPlaceholder = "مثال: ۳۰"
    override val frequencyXDays = "تکرار (هر X روز)"
    override val frequencyPlaceholder = "۱ برای روزانه، ۷ برای هفتگی"
    override val commitmentWeeks = "تعهد (تعداد هفته)"
    override val weeksPlaceholder = "مثال: ۱۲"
    override val hideOptionalDetails = "پنهان‌سازی جزئیات اختیاری 🔼"
    override val customizeDescriptionCosts = "سفارشی‌سازی توضیحات و هزینه‌ها 🔽"
    override val howWillYouDoThis = "چگونه این کار را انجام می‌دهید؟"
    override val completionCriteria = "معیار اتمام کار"
    override val completionCriteriaPlaceholder = "معیار اتمام کار چیست؟"
    override val selectIcon = "انتخاب یک آیکون"
    override val isThisLongTermAmbition = "آیا این یک هدف بلندمدت زندگی است؟"
    override val lifeGoalLabel = "هدف زندگی"
    override val customizeIconDescription = "سفارشی‌سازی آیکون و توضیحات 🔽"
    override val whatDoesAchievingMean = "رسیدن به این هدف چه معنایی برای شما دارد؟"
    override val aboutUs = "درباره ما"
    override val aboutUsScreenGoBack = "صفحه درباره ما - بازگشت"
    override val titleLabel = "عنوان"
    override val save = "ذخیره"
    override val createChallenges = "ایجاد چالش‌ها"
    override val goalBreakdown = "جزئیات هدف"
    override val masterGoalPrefix = "هدف اصلی: %s"
    override val milestonePrefix = "مرحله: %s"
    override val activeChallenges = "چالش‌های فعال"
    override val addMilestone = "افزودن مرحله"
    override val skillMilestone = "مرحله مهارتی"
    override val conferNewSkill = "آیا تکمیل این مرحله مهارت جدیدی به همراه دارد؟"
    override val whatIsMilestoneAbout = "این مرحله در مورد چیست؟"
    override val createChallengeForGoal = "ساختن چالش برای این هدف"
    override val actionGoalsCount = "اهداف اجرایی (%d)"
    override val noActionsDefined = "هیچ اقدامی برای این مرحله تعریف نشده است."
    override val completed = "کامل شده"
    override val dueTomorrow = "فردا انجام شود"
    override val activeChallengesCount = "چالش‌های فعال (%d)"
    override val addAction = "افزودن اقدام"
    override val dueDate = "تاریخ تحویل"
    override val tomorrow5pm = "فردا، ساعت ۵:۰۰ عصر"
    override val focus5dayStreak = "امروز روی تکمیل این کار تمرکز کنید تا زنجیره ۵ روزه خود را حفظ کنید!"
    override val description = "توضیحات"
    override val createMilestoneGoal = "ایجاد هدف مرحله‌ای"
    override val milestoneTitlePlaceholder = "مثال: یادگیری زبان آلمانی سطح B1"
    override val milestoneSuggestMvp = "ساخت MVP 🚀"
    override val milestoneSuggestBasics = "یادگیری مبانی 📚"
    override val milestoneSuggestSale = "اولین فروش 💰"
    override val milestoneSuggestTeam = "استخدام تیم 👥"
    override val milestoneSuggestBeta = "راه‌اندازی نسخه بتا 📢"
    override val milestoneSuggestFit = "رسیدن به تناسب اندام 🏃"
    override val parentGoalPrefix = "هدف والد ›"
    override val details = "جزئیات"
    override val stabilityConditions = "شرایط پایداری"
    override val suggestedSolutions = "راهکارهای پیشنهادی"
    override val viewAll = "مشاهده همه"
    override val noSolutionsFound = "هیچ راهکاری پیدا نشد. راهنمایی هوش مصنوعی را امتحان کنید یا تصمیم بگیرید."
    override val activeChallenge = "چالش فعال"
    override val noDescriptionChallenge = "هیچ توضیحی برای این چالش ارائه نشده است."
    override val aiHelp = "راهنمایی هوش مصنوعی"
    override val solution = "راهکار"
    override val decide = "تصمیم‌گیری"
    override val deleteChallenge = "حذف چالش"
    override val percentFormat = "%d٪"
    override val defaultStrategyRecommendation = "در هر زمان روی یک کار تمرکز کنید، عملیات اولیه را پیاده‌سازی کنید، سپس به سمت راهکارهای پیچیده حرکت کنید."

    // Presets & Days Short (Farsi)
    override val daysShort = listOf("د", "س", "چ", "پ", "ج", "ش", "ی")
    override val presetWorkoutLabel = "۳۰ دقیقه ورزش 🏃"
    override val presetWorkoutTitle = "۳۰ دقیقه ورزش"
    override val presetWorkoutDesc = "حرکت کنید! مقداری ورزش هوازی یا قدرتی انجام دهید."
    override val presetWorkoutCriteria = "ورزش را تمام کرده و در برنامه سلامت ثبت کنید"
    override val presetCodeLabel = "۱ ساعت کدنویسی 💻"
    override val presetCodeTitle = "۱ ساعت کدنویسی"
    override val presetCodeDesc = "کار تمرکزی روی پروژه‌های شخصی یا آموزش‌ها."
    override val presetCodeCriteria = "تغییرات را ثبت کنید یا ۱ درس برنامه‌نویسی را تمام کنید"
    override val presetReadLabel = "۱۵ دقیقه مطالعه 📚"
    override val presetReadTitle = "۱۵ دقیقه مطالعه"
    override val presetReadDesc = "یک کتاب غیرداستانی یا آموزشی بخوانید."
    override val presetReadCriteria = "حداقل ۱۰ صفحه بخوانید"
    override val presetMeditateLabel = "۱۰ دقیقه مدیتیشن 🧘‍♀️"
    override val presetMeditateTitle = "۱۰ دقیقه مدیتیشن"
    override val presetMeditateDesc = "دم و بازدم انجام دهید. تمرکز خود را روی زمان حال نگه دارید."
    override val presetMeditateCriteria = "یک جلسه ۱۰ دقیقه‌ای مدیتیشن راهنمایی شده را کامل کنید"
    override val presetReviewLabel = "مرور هفتگی 📈"
    override val presetReviewTitle = "مرور هفتگی"
    override val presetReviewDesc = "به پیشرفت این هفته نگاهی بیندازید و هفته بعد را برنامه‌ریزی کنید."
    override val presetReviewCriteria = "اقدامات و مراحل کامل شده را مرور کنید"
    override val presetGroceriesLabel = "خرید مایحتاج 🛒"
    override val presetGroceriesTitle = "خرید مایحتاج"
    override val presetGroceriesDesc = "به فروشگاه بروید و مواد اولیه سالم تهیه کنید."
    override val presetGroceriesCriteria = "همه اقلام لیست خرید را تهیه کنید"
}

val LocalAppStrings = staticCompositionLocalOf<AppStrings> { EnStrings }

