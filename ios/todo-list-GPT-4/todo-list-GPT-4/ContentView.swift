//
//  ContentView.swift
//  todo-list-GPT-4
//
//  Created by Alex D on 4/6/23.
//

import SwiftUI

struct ContentView: View {
    
    @StateObject private var viewModel: TaskViewModel
        
    init() {
        let viewContext = TaskStore.shared.container.viewContext
        let viewModel = TaskViewModel(viewContext: viewContext)
        _viewModel = StateObject(wrappedValue: viewModel)
    }
    
    var body: some View {
        TaskList(viewModel: viewModel)
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
