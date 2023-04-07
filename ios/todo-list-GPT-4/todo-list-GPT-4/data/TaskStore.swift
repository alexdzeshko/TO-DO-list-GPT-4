//
//  TaskStore.swift
//  todo-list-GPT-4
//
//  Created by Alex D on 4/6/23.
//

import Foundation
import SwiftUI
import CoreData

class TaskStore: ObservableObject {
    static let shared = TaskStore()
    let container: NSPersistentContainer
    
    init(inMemory: Bool = false) {
        container = NSPersistentContainer(name: "TaskModel")

        if inMemory {
            container.persistentStoreDescriptions.first?.url = URL(fileURLWithPath: "/dev/null")
        }

        container.loadPersistentStores(completionHandler: { (storeDescription, error) in
            if let error = error as NSError? {
                fatalError("Unresolved error \(error), \(error.userInfo)")
            }
        })
        
        prepopulateDataIfNeeded(context: container.viewContext)
    }
    
    func fetchTasks() -> [Task] {
        let request: NSFetchRequest<Task> = Task.fetchRequest()
        
        do {
            let tasks = try container.viewContext.fetch(request)
            return tasks.sorted { !$0.isCompleted && $1.isCompleted }
        } catch {
            print("Error fetching tasks: \(error.localizedDescription)")
            return []
        }
    }
    
    func addTask(title: String, description: String) {
        let task = Task(context: container.viewContext)
        task.id = UUID()
        task.title = title
        task.contentText = description
        task.isCompleted = false
        
        saveContext()
    }
    
    func toggleCompletion(for task: Task) {
        task.isCompleted.toggle()
        saveContext()
    }
    
    func saveContext() {
        do {
            try container.viewContext.save()
        } catch {
            print("Error saving context: \(error.localizedDescription)")
        }
    }
    
    static let preview: TaskStore = {
        let taskStore = TaskStore(inMemory: true)
        // You can add some sample data here, if needed
        return taskStore
    }()
    
    func prepopulateDataIfNeeded(context: NSManagedObjectContext) {
        let fetchRequest: NSFetchRequest<Task> = Task.fetchRequest()

        do {
            let taskCount = try context.count(for: fetchRequest)

            if taskCount == 0 {
                // No tasks found in the database, so insert initial data
                let initialTask1 = Task(context: context)
                initialTask1.title = "Sample Task 1"
                initialTask1.contentText = "This is an example task."
                initialTask1.isCompleted = false
                initialTask1.id = UUID()

                let initialTask2 = Task(context: context)
                initialTask2.title = "Sample Task 2"
                initialTask2.contentText = "This is another example task."
                initialTask2.isCompleted = true
                initialTask2.id = UUID()

                do {
                    try context.save()
                } catch {
                    print("Error saving initial tasks: \(error)")
                }
            }
        } catch {
            print("Error fetching task count: \(error)")
        }
    }
}
