# Fuel Frenzy Racing 🏁

An exciting arcade racing game for Android where you customize vehicles with powerful upgrades and create crazy fuel mixes from everyday food ingredients!

## Features

- **Vehicle Customization**: Cars, Trucks, and Semis with performance parts
- **Fuel Mixing System**: Create unique fuel mixes from condiments, sauces, and beverages  
- **Mile-Long Tracks**: Race on dynamically challenging tracks with curves
- **Multiple Difficulty Levels**: Easy, Medium, Hard, and Extreme
- **Realistic Physics**: Speed, acceleration, handling, and weight calculations
- **Progression System**: Earn coins and unlock vehicles

## Getting Started

### Prerequisites
- Android Studio
- Android SDK 24+
- Kotlin 1.9.0

### Installation

1. Clone the repository
2. Open in Android Studio
3. Sync Gradle files
4. Run on device or emulator

## Project Structure

```
app/
├── src/main/
│   ├── kotlin/com/fuelfrenzyracing/game/
│   │   ├── data/
│   │   │   ├── database/    # Room Database setup
│   │   │   └── models/      # Data classes
│   │   ├── domain/
│   │   │   ├── GameRepository.kt
│   │   │   └── GameEngine.kt
│   │   └── ui/
│   │       ├── activities/
│   │       └── adapters/
│   └── res/
│       ├── layout/
│       ├── values/
│       └── drawable/
```

## Game Mechanics

### Vehicles
- **Car**: Fast, agile, great acceleration
- **Truck**: Balanced performance
- **Semi**: Powerful, heavy

### Fuel Ingredients
- **Condiments**: Ketchup, Mustard, Mayo
- **Sauces**: Tater Sauce, Hot Sauce
- **Beverages**: Coke, Pepsi, Sprite
- **Energy Drinks**: Red Bull, Monster

Each ingredient affects speed, acceleration, fuel consumption, and weight!

## Technology Stack

- **Language**: Kotlin
- **Database**: Room (Android Architecture Components)
- **UI Framework**: AndroidX
- **Async**: Kotlin Coroutines
- **Build Tool**: Gradle

## How to Play

1. **Customize Vehicle**: Add performance parts to your vehicle
2. **Mix Fuel**: Select ingredients from different categories
3. **Prepare for Race**: Select a vehicle, mix fuel, choose track, set difficulty
4. **Race**: Compete on the mile-long track
5. **Earn Rewards**: Win coins based on your performance

## Difficulty Modifiers

| Level | Speed Multiplier | Coin Reward |
|-------|-----------------|----------|
| Easy | 1.0x | 1x |
| Medium | 0.85x | 1.5x |
| Hard | 0.70x | 2.0x |
| Extreme | 0.50x | 3.0x |

## Building for Google Play

### Requirements
- App signing configured
- Android target API 34+
- Privacy policy
- Content rating

### Build Commands

```bash
# Debug APK
./gradlew assembleDebug

# Release APK
./gradlew assembleRelease

# App Bundle
./gradlew bundleRelease
```

## Future Enhancements

- 🎮 Multiplayer racing modes
- 🌍 More tracks and environments
- 🏆 Global leaderboards
- 💎 Vehicle cosmetics
- 🔊 Sound & music
- 📊 Achievement system

## License

Proprietary - Fuel Frenzy Racing

## Support

For issues or questions, please open a GitHub issue.

---

**Ready to race?** Download Fuel Frenzy Racing now! 🏎️
