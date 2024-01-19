package ca.qc.castroguilherme.amaflorafb.models.plantdbmodel


import com.google.gson.annotations.SerializedName

data class DetailResponse(
//    @SerializedName("attracts")
//    val attracts: List<Any>,
//    @SerializedName("care-guides")
//    val careGuides: String,
    @SerializedName("care_level")
    val careLevel: String,
    @SerializedName("common_name")
    val commonName: String,
//    @SerializedName("cones")
//    val cones: Boolean,
//    @SerializedName("cuisine")
//    val cuisine: Boolean,
//    @SerializedName("cycle")
//    val cycle: String,
    @SerializedName("default_image")
    val defaultImage: DefaultImageX,
//    @SerializedName("depth_water_requirement")
//    val depthWaterRequirement: List<Any>,
    @SerializedName("description")
    val description: String,
//    @SerializedName("dimension")
//    val dimension: String,
//    @SerializedName("dimensions")
//    val dimensions: Dimensions,
//    @SerializedName("drought_tolerant")
//    val droughtTolerant: Boolean,
//    @SerializedName("edible_fruit")
//    val edibleFruit: Boolean,
//    @SerializedName("edible_fruit_taste_profile")
//    val edibleFruitTasteProfile: String,
//    @SerializedName("edible_leaf")
//    val edibleLeaf: Boolean,
//    @SerializedName("family")
//    val family: Any?,
//    @SerializedName("flower_color")
//    val flowerColor: String,
//    @SerializedName("flowering_season")
//    val floweringSeason: Any?,
//    @SerializedName("flowers")
//    val flowers: Boolean,
//    @SerializedName("fruit_color")
//    val fruitColor: List<Any>,
//    @SerializedName("fruit_nutritional_value")
//    val fruitNutritionalValue: String,
//    @SerializedName("fruits")
//    val fruits: Boolean,
//    @SerializedName("growth_rate")
//    val growthRate: String,
//    @SerializedName("hardiness")
//    val hardiness: Hardiness,
//    @SerializedName("hardiness_location")
//    val hardinessLocation: HardinessLocation,
//    @SerializedName("harvest_season")
//    val harvestSeason: Any?,
    @SerializedName("id")
    val id: Int,
//    @SerializedName("indoor")
//    val indoor: Boolean,
//    @SerializedName("invasive")
//    val invasive: Boolean,
//    @SerializedName("leaf")
//    val leaf: Boolean,
//    @SerializedName("leaf_color")
//    val leafColor: List<String>,
//    @SerializedName("maintenance")
//    val maintenance: Any?,
//    @SerializedName("medicinal")
//    val medicinal: Boolean,
//    @SerializedName("origin")
//    val origin: List<String>,
//    @SerializedName("other_images")
//    val otherImages: String,
//    @SerializedName("other_name")
//    val otherName: List<String>,
//    @SerializedName("pest_susceptibility")
//    val pestSusceptibility: List<Any>,
//    @SerializedName("pest_susceptibility_api")
//    val pestSusceptibilityApi: String,
//    @SerializedName("plant_anatomy")
//    val plantAnatomy: List<Any>,
//    @SerializedName("poisonous_to_humans")
//    val poisonousToHumans: Int,
//    @SerializedName("poisonous_to_pets")
//    val poisonousToPets: Int,
//    @SerializedName("propagation")
//    val propagation: List<String>,
//    @SerializedName("pruning_count")
//    val pruningCount: List<Any>,
//    @SerializedName("pruning_month")
//    val pruningMonth: List<String>,
//    @SerializedName("salt_tolerant")
//    val saltTolerant: Boolean,
    @SerializedName("scientific_name")
    val scientificName: List<String>,
//    @SerializedName("seeds")
//    val seeds: Int,
//    @SerializedName("soil")
//    val soil: List<Any>,
    @SerializedName("sunlight")
    val sunlight: List<String>,
//    @SerializedName("thorny")
//    val thorny: Boolean,
//    @SerializedName("tropical")
//    val tropical: Boolean,
//    @SerializedName("type")
//    val type: String,
//    @SerializedName("volume_water_requirement")
//    val volumeWaterRequirement: List<Any>,
    @SerializedName("watering")
    val watering: String,
    @SerializedName("watering_general_benchmark")
    val wateringGeneralBenchmark: WateringGeneralBenchmark,
//    @SerializedName("watering_period")
//    val wateringPeriod: Any?
)