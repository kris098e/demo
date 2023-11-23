package project.repository

interface RolesRepo {
    fun storeRoles(userId: Long, roles: List<String>)
    fun getRoles(userId: Long): List<String>
}