# VoidGenPlus

VoidGenPlus is a powerful fork of the VoidGen plugin that enhances void world generation with advanced features and full dimension support. The plugin enables server owners to create customized void and superflat customized worlds across all Minecraft dimensions (Overworld, Nether, and End) with precise control over generation settings.

## Quick Start

### Basic Usage
Create a void world using MultiVerse-Core:
```bash
/mv create <worldname> <environment> -g VoidGenPlus
```

Where:
- `<worldname>` is your desired world name
- `<environment>` is one of: normal, nether, or end
- `-g VoidGenPlus` specifies our void generator

Examples:
```bash
# Create an overworld void
/mv create voidworld normal -g VoidGenPlus

# Create a nether void
/mv create nethervoid nether -g VoidGenPlus

# Create an end void
/mv create endvoid end -g VoidGenPlus
```

### Advanced Configuration
You can customize the world generation by adding settings:
```bash
# Create a world with custom layers
/mv create flatworld normal -g VoidGenPlus -s "layers:minecraft:bedrock,2*minecraft:dirt,minecraft:grass_block"

# Create a nether world with custom layers
/mv create netherlayers nether -g VoidGenPlus -s "layers:minecraft:bedrock,5*minecraft:netherrack,minecraft:soul_sand"
```

## World Creation

When creating a world with Multiverse, use these world types:

- For normal worlds: `normal`
- For nether worlds: `nether`
- For end worlds: `end`

Examples:

```bash
# Create a normal void world
/mv create voidworld normal -g VoidGenPlus:126*minecraft:air,minecraft:bedrock;1;biome=minecraft:plains

# Create a nether void world
/mv create netherworld nether -g VoidGenPlus:126*minecraft:air,minecraft:bedrock;1;biome=minecraft:nether_wastes

# Create an end void world
/mv create endworld end -g VoidGenPlus:126*minecraft:air,minecraft:bedrock;1;biome=minecraft:end_highlands
```

The world type automatically sets the correct environment which affects:
- Sky color and fog
- Ambient lighting
- Special effects (like nether fog)
- Default world height limits

## Features

### Dimension Support
- Works in any dimension (Overworld, Nether, End)
- Proper height limits for each dimension
- Environment-aware generation settings

### Enhanced Configuration
- Per-dimension height limits
- Customizable build ranges
- Environment-specific biome settings
- Flexible generation parameters

### Build Height Control
- Configurable minimum and maximum heights per dimension
- Full build range support (-64 to 320)
- Dimension-specific default heights

### 🏗️ Custom Layer Generation
- Support for Minecraft's superflat layer format
- Define custom world layers using simple strings
- Mix and match with other generation settings
- Compatible with all dimensions

## Nether Portal Setup

When creating nether void worlds, you'll need to properly set up portal linking to avoid errors. 

## ⚙️ Configuration

### Basic Settings
```json
{
  "biome": "PLAINS",            // Custom biome (optional)
  "bedrock": true,             // Place bedrock at minimum height
  "minHeight": -64,            // Min build height
  "maxHeight": 320,            // Max build height
  "layers": "minecraft:bedrock,minecraft:dirt,minecraft:grass_block"  // Custom layers
}
```

## 🔧 Technical Details
- Java Version: 21
- Build System: Gradle
- Key Dependencies:
  * Bukkit/Spigot API (1.21.3-R0.1-SNAPSHOT)
  * Apache Commons Lang3 (3.14.0)
  * Google Gson (2.10.1)
